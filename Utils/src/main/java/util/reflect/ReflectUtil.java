package util.reflect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import exception.UtilException;
import util.JavaUtil;
import util.reflect.helper.FieldInfo;
import util.reflect.helper.PathHolder;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2024/4/8
 * @description: 反射工具类
 */
public class ReflectUtil {
    /**
     * 缓存类的递归属性信息
     * */
    private static final Map<Class<?>, List<FieldInfo>> cache = new ConcurrentHashMap<>();
    /**
     * 缓存类的属性
     * */
    private static final Map<Class<?>,List<Field>>cacheField=new ConcurrentHashMap<>();

    /**
     * 通过类型寻找属性
     */
    public static Field findField(Class<?> clazz, Class<?> filedClazz) {
        List<Field> fields = getFields(clazz);
        Optional<Field> first = fields.stream().filter(item -> item.getType().isAssignableFrom(filedClazz)).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        throw new UtilException(StrUtil.format("未在类{}中发现类型为{}的属性", clazz.getName(), filedClazz.getName()));

    }

    /**
     * 寻找属性 fieldName属性名 isIgnoreCase是否忽略属性名的大小写
     */
    public static Field findField(Class<?> clazz, String fieldName, boolean isIgnoreCase) {
        List<Field> fields = getFields(clazz);
        Optional<Field> first = fields.stream().filter(item -> {
                    if (isIgnoreCase) {
                        return item.getName().equalsIgnoreCase(fieldName);
                    }
                    return item.getName().equals(fieldName);
                }
        ).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        throw new UtilException(StrUtil.format("未在类{}中发现属性{}", clazz.getName(), fieldName));
    }


    /**
     * 获取对象中的所有属性的值 属性名作为 key
     */
    public static Map<String, Object> getValues(Object o) {
        Map<String, Object> result = new HashMap<>();
        List<Field> fields = getFields(o.getClass());
        for (Field field : fields) {
            Object o1 = null;
            try {
                o1 = field.get(o);
            } catch (Exception e) {
                throw new UtilException("未找到指定属性", e);
            }
            result.put(field.getName(), o1);
        }
        return result;
    }

    /**
     * 获取对象中的所有属性
     */
    public static List<Field> getFields(Class<?> clazz) {
        if(cacheField.containsKey(clazz)){
            if(CollUtil.isNotEmpty(cacheField.get(clazz))){
                return cacheField.get(clazz);
            }
        }
        List<Field> result = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            result.add(field);
        }
        cacheField.put(clazz,result);
        return result;
    }

    /**
     * 根据注解获取属性
     */
    public static List<Field> getFields(Class<?> clazz, Class<? extends Annotation> ann) {
        List<Field> result = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(ann)) {
                continue;
            }
            field.setAccessible(true);
            result.add(field);
        }
        return result;
    }
    /**
     * 获取类中所有方法
     * */
    public static List<Method> getMethod(Class<?>clazz){
        Method[] declaredMethods = clazz.getDeclaredMethods();
        if(ArrayUtil.isEmpty(declaredMethods)){
            return new ArrayList<>();
        }
        List<Method> methods = Arrays.stream(declaredMethods)
                .filter(item -> !JavaUtil.isBaseMethod(item))
                .peek(item->item.setAccessible(true))
                .collect(Collectors.toList());
        return methods;
    }
    /**
     * 获取类中带有特定注解的方法
     * */
    public static List<Method> getMethod(Class<?>clazz,Class<? extends Annotation>ann){
        return getMethod(clazz).stream().filter(item->item.isAnnotationPresent(ann)).collect(Collectors.toList());
    }

    /**
     * 获取某个类的 第一个继承接口的第一个泛型
     */
    public static Class<?> getFirstInterFaceFirstGeneric(Object o) throws ClassNotFoundException {
        ParameterizedType genericInterface = (ParameterizedType) o.getClass().getGenericInterfaces()[0];
        Type actualTypeArgument = genericInterface.getActualTypeArguments()[0];
        String typeName = actualTypeArgument.getTypeName();
        return Class.forName(typeName);
    }

    /**
     * 获取该class的第一个父类接口的第一个泛型类型
     */
    public static Class<?> getFirstInterFaceFirstGeneric(Class<?> clazz) throws ClassNotFoundException {
        ParameterizedType genericInterface = (ParameterizedType) clazz.getGenericInterfaces()[0];
        Type actualTypeArgument = genericInterface.getActualTypeArguments()[0];
        String typeName = actualTypeArgument.getTypeName();
        return Class.forName(typeName);
    }
    /**
     * 获取list属性的泛型
     * */
    public static Class<?>getListFieldGeneric(Field field){
        if(ObjectUtil.isEmpty(field)||!List.class.isAssignableFrom(field.getType())){
            return null;
        }
        ParameterizedType genericType = (ParameterizedType)field.getGenericType();
        Type actualTypeArgument = genericType.getActualTypeArguments()[0];
        try {
            return Class.forName(actualTypeArgument.getTypeName());
        } catch (ClassNotFoundException e) {
            throw new UtilException(e);
        }
    }

    /**
     * 递归获取类中属性
     */
    public static List<FieldInfo> loopGetFieldInfos(Class<?> clazz) {
        if (cache.containsKey(clazz)) {
            if (CollUtil.isNotEmpty(cache.get(clazz))) {
                return cache.get(clazz);
            }
        }
        PathHolder pathHolder=new PathHolder();
        pathHolder.push(clazz.getSimpleName());
        List<FieldInfo> fieldInfos = loopGetFieldInfo(clazz,pathHolder);
        cache.put(clazz, fieldInfos);
        return fieldInfos;
    }


    private static List<FieldInfo> loopGetFieldInfo(Class<?> clazz,PathHolder pathHolder) {
        if (ObjectUtil.isEmpty(clazz)) {
            return null;
        }
        List<Field> fields = getFields(clazz);
        List<FieldInfo> result = new ArrayList<>();
        for (Field field : fields) {
            FieldInfo fieldInfo = loopGetFieldInfo(field,pathHolder);
            if (ObjectUtil.isNotEmpty(fieldInfo)) {
                result.add(fieldInfo);
            }
        }
        return result;
    }

    private static FieldInfo loopGetFieldInfo(Field field,PathHolder pathHolder) {
        if (ObjectUtil.isEmpty(field)) {
            return null;
        }
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.setField(field);
        fieldInfo.setType(field.getType());
        fieldInfo.setName(field.getName());
        fieldInfo.setPath(pathHolder.getPath()+field.getName());
        if (JavaUtil.isSpecPojo(field.getType()) || JavaUtil.isBaseTypeOrPackType(field.getType()) || String.class.isAssignableFrom(field.getType())) {
            fieldInfo.setList(false);
            return fieldInfo;
        }
        if (List.class.isAssignableFrom(field.getType())) {
            try {
                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                Class<?> aClass = Class.forName(parameterizedType.getActualTypeArguments()[0].getTypeName());
                fieldInfo.setList(true);
                fieldInfo.setGenericType(aClass);
                if (!JavaUtil.isSpecPojo(aClass) && !JavaUtil.isBaseTypeOrPackType(aClass)) {
                    pathHolder.push(field.getName());
                    pathHolder.push(aClass.getSimpleName());
                    List<FieldInfo> fieldInfos = loopGetFieldInfo(aClass,pathHolder);
                    pathHolder.pop();
                    pathHolder.pop();
                    fieldInfo.addAllChild(fieldInfos);
                }
                return fieldInfo;
            } catch (ClassNotFoundException ignore) {
                return null;
            }
        }
        fieldInfo.setList(false);
        pathHolder.push(field.getName());
        List<FieldInfo> fieldInfos = loopGetFieldInfo(field.getType(),pathHolder);
        pathHolder.pop();
        fieldInfo.addAllChild(fieldInfos);
        return fieldInfo;
    }
}
