package util.validate;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import util.JavaUtil;
import util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2024/4/26
 * @description: 校验工具类
 */
@SuppressWarnings("all")
public class ValidateUtil {
    /**
     * 递归对象 判断带有注解{@link NotEmpty} 的属性是否为空、
     * @param o 校验的对象
     * @param selfFailMsg 对象本身为空时失败话术
     * */
    public static void loopValidate(Object o,String selfFailMsg) {
        if(o==null){
            throw new RuntimeException(selfFailMsg);
        }
        List<Field> fields = ReflectUtil.getFields(o.getClass(), NotEmpty.class);
        for (Field field : fields) {
            NotEmpty annotation = field.getAnnotation(NotEmpty.class);
            try {
                validate(field.get(o),annotation);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }

    }
    private static void validate(Object o,NotEmpty ann){
        if(o==null){
            throw new RuntimeException(ann.failMsg());
        }
        try {
            if (JavaUtil.isSpecPojo(o.getClass()) || JavaUtil.isBaseTypeOrPackType(o.getClass())) {
                if (!validateSpec(o, ann)) {
                    throw new RuntimeException(ann.failMsg());
                }
                return;
            }
            if (List.class.isAssignableFrom(o.getClass())) {
                if (!validateList((List<?>)o,ann)) {
                    throw new RuntimeException(ann.failMsg());
                }
                return;
            }
            if(Map.class.isAssignableFrom(o.getClass())){
                Map map=(Map) o;
                Collection values = map.values();
                for (Object value : values) {
                    validate(value,ann);
                }
            }
            //递归对象
            loopValidate(o,ann.failMsg());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static boolean validateList(List<?>list,NotEmpty ann) throws IllegalAccessException, ClassNotFoundException {
        if (list==null) {
            return false;
        }
        if(ann.isBanListSizeZero()&&list.size()==0){
            return false;
        }
        for (Object o1 : list) {
            validate(o1,ann);
        }
        return true;
    }
    private static boolean validateSpec(Object o, NotEmpty annotation) throws IllegalAccessException {
        if (String.class.isAssignableFrom(o.getClass())) {
            if (!validateStr(o, annotation)) {
                return false;
            }
            return true;
        }
        if (ObjectUtil.isEmpty(o)) {
            return false;
        }
        return true;
    }

    private static boolean validateStr(Object o, NotEmpty annotation) throws IllegalAccessException {
        String str = (String) o;
        if (str == null) {
            return false;
        }
        if (annotation.isBanStrBlank() && StrUtil.isBlank(str)) {
            return false;
        }
        if (annotation.isBanStrUndefined() && "undefined".equalsIgnoreCase(str)) {
            return false;
        }
        return true;

    }
}
