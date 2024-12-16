package util.field;

import util.FieldUtilParent;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2023/8/24
 * @description: 属性操作包
 */
public class FieldUtil extends FieldUtilParent {

    public static List<Field> getFields(Object o) {
        return getFields(o.getClass());
    }

    public static List<Field> getFields(Object o, String... filedName) {
        return getFields(o).stream().filter(field -> Arrays.asList(filedName).contains(field.getName())).collect(Collectors.toList());
    }

    public static List<Field> getFields(Class<?> clazz) {
        //判断对象是否是集合对象
        if (List.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("不可获取集合中的属性");
        }
        List<Field> fieldsList = Arrays.asList(clazz.getDeclaredFields());
        setFieldsAccessibleTrue(fieldsList);
        return fieldsList;
    }

    public static Field getTargetField(Object o, String fieldName) {
        return getTargetField(o.getClass(), fieldName);
    }

    public static Field getTargetField(Class<?> clazz, String fieldName) {
        return getFields(clazz).stream().filter(field -> field.getName().equalsIgnoreCase(fieldName)).findFirst().orElse(null);
    }

    /**
     * 设置新值给对象中的某个属性
     */
    public static void setFieldToObject(Object o, String fieldName, Object value) throws IllegalAccessException {
        getTargetField(o, fieldName).set(o, value);
    }



    /**
     * 判断属性是不是基本数据类型
     * */
    public static boolean judgeFieldIsCommonType(Field field) {
        if (field.getType().isAssignableFrom(int.class)) {
            return true;
        }
        if (field.getType().isAssignableFrom(boolean.class)) {
            return true;
        }
        if (field.getType().isAssignableFrom(char.class)) {
            return true;
        }
        if (field.getType().isAssignableFrom(float.class)) {
            return true;
        }
        if (field.getType().isAssignableFrom(double.class)) {
            return true;
        }
        if (field.getType().isAssignableFrom(long.class)) {
            return true;
        }
        if (field.getType().isAssignableFrom(short.class)) {
            return true;
        }
        if (field.getType().isAssignableFrom(byte.class)) {
            return true;
        }
        return false;
    }



}
