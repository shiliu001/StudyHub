package util;

import testutil.testmain.Main;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2023/8/24
 * @description: TODO
 */
public class FieldUtilParent {
    protected static void setFieldsAccessibleTrue(List<Field>fields){
        fields.parallelStream().forEach(field->field.setAccessible(true));
    }

    public static void main(String[] args) {
        BigDecimal b1=new BigDecimal("0.1");
        BigDecimal b2=new BigDecimal("0.10");
        System.out.println(b1.equals(b2));
        System.out.println(b1.compareTo(b2));
        System.out.println(2147483647+2147483647);
    }
}
