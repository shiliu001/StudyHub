package util.compare;

import testutil.testentity.School;

import java.lang.reflect.Field;

/**
 * 自定义逻辑接口类
 * 通过注解{@link UseMyCompare} 将子类注释在属性上面可以执行自己的比较逻辑
 * 例子：{@link School#getPresidentName()}
 * */
public interface CustomCompareLogic {
    boolean doCompare(Object o1, Object o2,Field f) throws IllegalAccessException;
}
