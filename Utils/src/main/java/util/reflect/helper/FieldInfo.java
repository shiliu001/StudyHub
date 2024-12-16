package util.reflect.helper;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2024/4/13
 * @description: 属性封装信息
 */
@SuppressWarnings("all")
@Getter
@Setter
public class FieldInfo {
    private Field field;
    /**
     * 属性名
     */
    private String name;
    /**
     * 属性路径
     */
    private String path;
    /**
     * 类型
     */
    private Class<?> type;
    /**
     * 是否是集合
     */
    private boolean isList;
    /**
     * 泛型类型
     */
    private Class<?> genericType;
    /**
     * 子属性
     */
    private final List<FieldInfo> childInfos = new ArrayList<>();

    public void addChild(FieldInfo fieldInfo) {
        if (ObjectUtil.isEmpty(fieldInfo)) {
            return;
        }
        childInfos.add(fieldInfo);
    }

    public void addAllChild(List<FieldInfo> fieldInfos) {
        fieldInfos = fieldInfos.stream().filter(ObjectUtil::isNotEmpty).collect(Collectors.toList());
        childInfos.addAll(fieldInfos);
    }

    public Object getFieldValue(Object o) {
        try {
            return field.get(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setFieldValue(Object o, Object value) {
        try {
            field.set(o, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Object getObject(){
        try {
            return type.newInstance();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public Object getGenericObject(){
        try {
            return genericType.newInstance();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public boolean isEmptyVaule(Object o){
        try {
            Object o1 = field.get(o);
            return ObjectUtil.isEmpty(o1);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
