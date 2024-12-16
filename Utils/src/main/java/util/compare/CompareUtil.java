package util.compare;

import util.field.FieldUtil;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2023/8/24
 * @description: TODO
 */
public class CompareUtil {


    /**
     * 比较两个对象是否一致
     */
    public static <T> boolean compareFieldValue(T o1, T o2) throws IllegalAccessException, InstantiationException {
        List<Field> fields = FieldUtil.getFields(o1);
        for (Field field : fields) {
            if (field.isAnnotationPresent(UseMyCompare.class)) {
                UseMyCompare declaredAnnotation = field.getDeclaredAnnotation(UseMyCompare.class);
                CustomCompareLogic useMyCompare = declaredAnnotation.comparator().newInstance();
                if( useMyCompare.doCompare(o1, o2, field)){
                    continue;
                }else {
                    return false;
                }
            }
            if (!field.getType().isAssignableFrom(field.getType())) {
                return false;
            }
            if (field.get(o1) == null && field.get(o2) == null) {
                continue;
            }
            if (field.get(o1) == null || field.get(o2) == null) {
                continue;
            }
            if (String.class.isAssignableFrom(field.getType()) && String.class.isAssignableFrom(field.getType())) {
                 if(field.get(o1).equals(field.get(o2))){
                     continue;
                 }else {
                     return false;
                 }
            }
            if (FieldUtil.judgeFieldIsCommonType(field) && FieldUtil.judgeFieldIsCommonType(field)) {
                if(field.get(o1) == field.get(o2)){
                    continue;
                }else {
                    return false;
                }
            }
            if (List.class.isAssignableFrom(field.getType()) && List.class.isAssignableFrom(field.getType())) {
                List list1 = (List) field.get(o1);
                List list2 = (List) field.get(o2);
                if (list1.size() != list2.size()) {
                    return false;
                }
                if (list1.size() == 0) {
                    continue;
                }
                //类型不同
                if (!list1.get(0).getClass().isAssignableFrom(list2.get(0).getClass())) {
                    return false;
                }
                for (int i = 0; i < list1.size(); i++) {
                    Object l1 = list1.get(i);
                    Object l2 = list2.get(i);
                    if( !compareFieldValue(l1, l2)){
                        return false;
                    }
                }
                return true;
            }
           if(!compareFieldValue(field.get(o1), field.get(o2))) {
               return false;
           }
        }
        return true;
    }
}
