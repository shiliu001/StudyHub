package util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2024/3/13
 * @description: 解析java文件的工具类
 */
public class JavaUtil {
    static List<Class<?>> javaBaseType = CollUtil.toList(int.class, long.class, short.class, byte.class, boolean.class, double.class, float.class, char.class);
    static List<Class<?>> javaBasePackType = CollUtil.toList(Integer.class, Long.class, Short.class, Byte.class, Boolean.class, Double.class, Float.class, Character.class);

    static List<Class<?>> isSpecPojoObjects = CollUtil.toList(String.class, BigDecimal.class, Timestamp.class, Date.class, DateTime.class, Calendar.class);

    static List<String> javaBaseMethod = CollUtil.toList("hashCode", "equals", "clone", "toString", "finalize", "wait", "notify", "notifyAll");

    /**
     * 转换成字符串
     */
    public static String valueToStr(Object o) {
        if (String.class.isAssignableFrom(o.getClass())) {
            return (String) o;
        }
        if (BigDecimal.class.isAssignableFrom(o.getClass())) {
            BigDecimal bigDecimal = (BigDecimal) o;
            return bigDecimal.toString();
        }
        if (Date.class.isAssignableFrom(o.getClass())) {
            Date date = (Date) o;
            return DateUtil.format(date, DatePattern.PURE_DATETIME_MS_PATTERN);
        }
        if (!isBaseTypeOrPackType(o)) {
            return null;
        }
        return String.valueOf(o);
    }

    private Object strToValue(Class<?> clazz, String str) {
        if (int.class.isAssignableFrom(clazz) || Integer.class.isAssignableFrom(clazz)) {
            return Integer.parseInt(str);
        }
        if (String.class.isAssignableFrom(clazz)) {
            return str;
        }
        if (BigDecimal.class.isAssignableFrom(clazz)) {
            return new BigDecimal(str);
        }
        if (boolean.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz)) {
            return Boolean.parseBoolean(str);
        }
        if (long.class.isAssignableFrom(clazz) || Long.class.isAssignableFrom(clazz)) {
            return Long.parseLong(str);
        }
        if (Date.class.isAssignableFrom(clazz)) {
            return DateUtil.parseDateTime(str);
        }

        return null;
    }

    /**
     * 是否为基本数据类型
     */
    public static boolean isBaseTypeOrPackType(Object o) {
        return isBaseTypeOrPackType(o.getClass());
    }

    /**
     * 是否是Object的方法
     */
    public static boolean isBaseMethod(Method method) {
        String name = method.getName();
        return javaBaseMethod.contains(name);
    }

    public static boolean isBaseType(Class<?> o) {
        return javaBaseType.stream().anyMatch(item -> item.isAssignableFrom(o));
    }

    public static boolean isBaseTypeOrPackType(Class<?> o) {
        boolean isBaseType = javaBaseType.stream().anyMatch(item -> item.isAssignableFrom(o));
        boolean isBasePackType = javaBasePackType.stream().anyMatch(item -> item.isAssignableFrom(o));
        return isBaseType || isBasePackType;
    }

    /**
     * 判断当前对象是否是特殊的对象
     */
    public static boolean isSpecPojo(Object o) {
        return isSpecPojo(o.getClass());
    }

    public static boolean isSpecPojo(Class<?> o) {
        return isSpecPojoObjects.stream().anyMatch(item -> item.isAssignableFrom(o));
    }

    /**
     * 字符串转换成指定类型
     */
    public static Object strToTargetType(String str, Class<?> a) {
        if (StrUtil.isEmpty(str)) {
            return null;
        }
        if (String.class.isAssignableFrom(a)) {
            return str;
        }
        if (BigDecimal.class.isAssignableFrom(a)) {
            return new BigDecimal(str);
        }
        if (int.class.isAssignableFrom(a) || Integer.class.isAssignableFrom(a)) {
            return Integer.parseInt(str);
        }
        if (long.class.isAssignableFrom(a) || Long.class.isAssignableFrom(a)) {
            return Long.parseLong(str);
        }
        if (Date.class.isAssignableFrom(a)) {
            return DateUtil.parseDateTime(str).toJdkDate();
        }
        if (Timestamp.class.isAssignableFrom(a)) {
            return Timestamp.valueOf(str);
        }
        if (boolean.class.isAssignableFrom(a) || Boolean.class.isAssignableFrom(a)) {
            return Boolean.parseBoolean(str);
        }
        return str;
    }
}
