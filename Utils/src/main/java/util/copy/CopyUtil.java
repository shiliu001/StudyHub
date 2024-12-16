package util.copy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import exception.CommonException;
import util.JavaUtil;
import util.copy.helper.CopyReport;
import util.reflect.ReflectUtil;
import util.reflect.helper.FieldInfo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2024/4/12
 * @description: 复制操作工具类
 */
@SuppressWarnings("all")
public class CopyUtil {
    public <T> CopyReport<T> copy(Object source, Class<T> target) {
        if (ObjectUtil.isEmpty(source) || ObjectUtil.isEmpty(target)) {
            return null;
        }
        try {
            T t = target.newInstance();
            CopyReport<T> copyReport = new CopyReport<>(t);
            copy(source, t, copyReport, true,new ArrayList<>(),new ArrayList<>());
            return copyReport;
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }

    /**
     * coverTargetValue是否覆盖目标的原值  基本数据类型必覆盖
     * 比如 coverTargetValue为true的情况下 source有个id有值 target也有个id有值 source会覆盖target中id值
     */
    public static <T> CopyReport<T> copy(Object source, T target, boolean coverTargetValue) {
        CopyReport<T> copyReport = new CopyReport<>(target);
        copy(source, target, copyReport, coverTargetValue,new ArrayList<>(),new ArrayList<>());
        return copyReport;
    }
    /**
     * ignoreFiledNames忽略赋值的target属性名
     * 默认覆盖原值
     * */
    public static <T> CopyReport<T> copy(Object source, T target,List<String> ignoreFiledNames) {
        CopyReport<T> copyReport = new CopyReport<>(target);
        copy(source, target, copyReport, true,ignoreFiledNames,new ArrayList<>());
        return copyReport;
    }
    /**
     * ignoreFiledNames忽略赋值的target属性名
     * coverTargetValue  原值是否允许被覆盖 基本数据类型必覆盖
     * */
    public static <T> CopyReport<T> copy(Object source, T target, boolean coverTargetValue,List<String> ignoreFiledNames) {
        CopyReport<T> copyReport = new CopyReport<>(target);
        copy(source, target, copyReport, coverTargetValue,ignoreFiledNames,new ArrayList<>());
        return copyReport;
    }
    /**
     * copy指定target的属性名
     * coverTargetValue  原值是否允许被覆盖 基本数据类型必覆盖
     * */
    public static <T> CopyReport<T> copyTargetField(Object source, T target, boolean coverTargetValue,List<String> targetFiledNames) {
        CopyReport<T> copyReport = new CopyReport<>(target);
        copy(source, target, copyReport, coverTargetValue,new ArrayList<>(),targetFiledNames);
        return copyReport;
    }


    private static void copy(Object source, Object target, CopyReport<?> copyReport, boolean coverTargetValue,List<String> ignoreFiledNames,List<String>targetFiledNames) {
        if (ObjectUtil.isEmpty(source) || ObjectUtil.isEmpty(target)) {
            return;
        }
        if(CollUtil.isEmpty(ignoreFiledNames)){
            ignoreFiledNames=new ArrayList<>();
        }
        ignoreFiledNames=ignoreFiledNames.stream().filter(StrUtil::isNotBlank).collect(Collectors.toList());
        List<FieldInfo> sourceFieldInfos = ReflectUtil.loopGetFieldInfos(source.getClass());
        List<FieldInfo> targetFieldInfos = ReflectUtil.loopGetFieldInfos(target.getClass());
        Map<String, Pair<FieldInfo, FieldInfo>> sourceFieldToTargetField = new HashMap<>();
        for (FieldInfo targetFieldInfo : targetFieldInfos) {
            //如果忽略了某些元素
            if(ignoreFiledNames.contains(targetFieldInfo.getName())){
                continue;
            }
            //如果特殊指定了属性 以指定为准
            if(CollUtil.isNotEmpty(targetFiledNames)&&!targetFiledNames.contains(targetFieldInfo.getName())){
                continue;
            }
            Optional<FieldInfo> match = sourceFieldInfos.stream().filter(sourceFieldInfo -> sourceFieldInfo.getName().equals(targetFieldInfo.getName())).findFirst();
            if (match.isPresent()) {
                sourceFieldToTargetField.put(targetFieldInfo.getName(), new Pair<>(match.get(), targetFieldInfo));
                continue;
            }
            copyReport.addNotCopyFormSource(targetFieldInfo.getPath());
        }
        List<String> finalIgnoreFiledNames = ignoreFiledNames;
        if(MapUtil.isEmpty(sourceFieldToTargetField)){
            return;
        }
        sourceFieldToTargetField.forEach((key, pair) -> {
            //源数据属性
            FieldInfo sourceFiledInfo = pair.getKey();
            //目标数据属性
            FieldInfo targetFiledInfo = pair.getValue();
            //假如其中一个是list 而另一个不是list 默认转换失败
            if (targetFiledInfo.isList() && !sourceFiledInfo.isList()) {
                copyReport.addConvertFailed(targetFiledInfo.getPath(), sourceFiledInfo.getPath());
                return;
            }
            if (!targetFiledInfo.isList() && sourceFiledInfo.isList()) {
                copyReport.addConvertFailed(targetFiledInfo.getPath(), sourceFiledInfo.getPath());
                return;
            }
            Object fieldValue = targetFiledInfo.getFieldValue(target);
            //如果两者都为list的情况下
            if (targetFiledInfo.isList()) {
                List<?> targetValue = (List<?>) targetFiledInfo.getFieldValue(target);
                List<Object> newTargetValues = new ArrayList<>();
                targetFiledInfo.setFieldValue(target, newTargetValues);
                //目标list属性的泛型类型
                Class<?> targetListType = targetFiledInfo.getGenericType();
                //获取源数据的list对象
                List<Object> sourceValues = (List<Object>) sourceFiledInfo.getFieldValue(source);
                //不允许覆盖原有值
                if (CollUtil.isNotEmpty(targetValue) && !coverTargetValue&&!JavaUtil.isBaseType(targetFiledInfo.getType())) {
                    return;
                }
                //如果是基本数据类型 或者一些特殊的对象类型 比如String BigDecimal等 这些对象不需要递归
                if (JavaUtil.isBaseTypeOrPackType(targetListType) || JavaUtil.isSpecPojo(targetListType)) {
                    if (CollUtil.isNotEmpty(sourceValues)) {
                        for (Object sourceValue : sourceValues) {
                            try {
                                if (sourceValue == null) {
                                    newTargetValues.add(null);
                                }
                                String sourceValueStr = JavaUtil.valueToStr(sourceValue);
                                newTargetValues.add(JavaUtil.strToTargetType(sourceValueStr, targetFiledInfo.getGenericType()));
                            } catch (Exception e) {
                                copyReport.addConvertFailed(sourceFiledInfo.getPath(), targetFiledInfo.getPath());
                                return;
                            }
                        }
                    } else {
                        if (sourceValues == null) {
                            sourceFiledInfo.setFieldValue(source, null);
                            return;
                        }
                        if (sourceValues.size() == 0) {
                            sourceFiledInfo.setFieldValue(source, new ArrayList<>());
                        }
                    }
                    return;
                }
                if (CollUtil.isEmpty(sourceValues)) {
                    return;
                }
                //循环源数据list中的对象属性
                for (Object sourceValue : sourceValues) {
                    Object genericObject = targetFiledInfo.getGenericObject();
                    newTargetValues.add(genericObject);
                    copy(sourceValue, genericObject, copyReport, coverTargetValue, finalIgnoreFiledNames,targetFiledNames);
                }
                return;
            }
            if (String.class.isAssignableFrom(targetFiledInfo.getType())) {
                if (ObjectUtil.isNotEmpty(fieldValue) && !coverTargetValue&&!JavaUtil.isBaseType(targetFiledInfo.getType())) {
                    return;
                }
                try {
                    Object sourceValue = sourceFiledInfo.getFieldValue(source);
                    if (sourceValue == null) {
                        return;
                    }
                    if(StrUtil.isBlank((String)sourceValue)){
                        targetFiledInfo.setFieldValue(target,sourceValue);
                        return;
                    }
                    String sourceValueStr = JavaUtil.valueToStr(sourceValue);
                    targetFiledInfo.setFieldValue(target, JavaUtil.strToTargetType(sourceValueStr, targetFiledInfo.getType()));
                    return;
                } catch (Exception e) {
                    copyReport.addConvertFailed(sourceFiledInfo.getPath(), targetFiledInfo.getPath());
                    return;
                }
            }
            //没有子属性 说明是普通对象
            if (CollUtil.isEmpty(targetFiledInfo.getChildInfos())) {
                if (ObjectUtil.isNotEmpty(fieldValue) && !coverTargetValue&&!JavaUtil.isBaseType(targetFiledInfo.getType())) {
                    return;
                }
                try {
                    Object sourceValue = sourceFiledInfo.getFieldValue(source);
                    if (ObjectUtil.isEmpty(sourceValue)) {
                        return;
                    }
                    String sourceValueStr = JavaUtil.valueToStr(sourceValue);
                    targetFiledInfo.setFieldValue(target, JavaUtil.strToTargetType(sourceValueStr, targetFiledInfo.getType()));
                    return;
                } catch (Exception e) {
                    copyReport.addConvertFailed(sourceFiledInfo.getPath(), targetFiledInfo.getPath());
                    return;
                }
            }
            //从这里开始说明是对象了 需要递归赋值
            Object sourceValue = sourceFiledInfo.getFieldValue(source);
            if (ObjectUtil.isEmpty(fieldValue)) {
                fieldValue = targetFiledInfo.getObject();
                targetFiledInfo.setFieldValue(target, fieldValue);
            }
            copy(sourceValue, fieldValue, copyReport, coverTargetValue,finalIgnoreFiledNames,targetFiledNames);
        });
    }


//    private static void baseFillValue(Field sourceField, Field targetField, Object source, Object target, boolean coverTargetValue) throws IllegalAccessException {
//        try {
//            Object newSource = sourceField.get(source);
//            Object newTarget = targetField.get(target);
//            if (ObjectUtil.isNotEmpty(newTarget) && !coverTargetValue) {
//                return;
//            }
//            if (sourceField.getType().isAssignableFrom(sourceField.getType())) {
//                targetField.set(target, newSource);
//                return;
//            }
//            String str = JavaUtil.valueToStr(newSource);
//            newTarget = JavaUtil.strToTargetType(str, targetField.getType());
//            targetField.set(target, newTarget);
//        } catch (Exception e) {
//            throw new CommonException(e);
//        }
//
//    }

}
