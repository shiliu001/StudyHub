package testutil.testentity;

import util.compare.CustomCompareLogic;

import java.lang.reflect.Field;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2023/8/24
 * @description: TODO
 */
public class StringCompare implements CustomCompareLogic {
    @Override
    public boolean doCompare(Object o1, Object o2, Field f) throws IllegalAccessException {
       String s1=(String) f.get(o1);
        String s2=(String) f.get(o2);
        return s1.equalsIgnoreCase(s2);
    }
}
