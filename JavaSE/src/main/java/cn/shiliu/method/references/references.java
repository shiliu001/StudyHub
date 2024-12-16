package cn.shiliu.method.references;

import lombok.Data;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

/**
 * @author shiliu
 * @date 2024/4/30
 * @apiNote 方法引用
 */
public class references {

    public static void main(String[] args) {
        Student stu = new Student();
        stu.setId(0);
        stu.setName("李四1");
        //获取学生姓名
        extracted(stu);
        //compare比对两个值是不是一致
        extracted1();
        //BiPredicate接口比较两个字符串
        extracted2();
    }

    /**
     * 供给型接口传值
     * 格式 对象名::实现方法（非静态方法）
     *
     * @param stu
     */
    private static void extracted(Student stu) {
        Supplier<String> runnable = () -> stu.getName();
        System.out.println(runnable);
        System.out.println(runnable.get());
        // 方法引用实现  当要传递给Lambda体的操作，已经有了实现方法，可以使用方法引用.   格式 对象名::实例方法（非静态方法）
        Supplier<String> runnable2 = stu::getName;
        System.out.println(runnable2);
        System.out.println(runnable2.get());
    }

    /**
     * 利用Comparator接口实现比较两个整型数据的大小
     * 类::静态方法名
     */
    private static void extracted1() {
        Comparator<Integer> com1 = (x, y) -> Integer.compare(x, y);
        System.out.println(com1.compare(100, 200));
        // 方法引用实现
        Comparator<Integer> com2 = Integer::compare;
        System.out.println(com2.compare(100, 200));
    }

    /**
     * BiPredicate接口比较两个字符串
     * 类::实例方法（非静态方法）
     * 使用前提
     * 方法的第一个参数(这里即为a)是被调用方法(这里指equals())的调用者；方法的第二个参数(这里指b)是被调用方法的参数.
     */
    private static void extracted2() {
        BiPredicate<String, String> pr = (a, b) -> a.equals(b);
        System.out.println(pr.test("shiliu", "shiliu"));
        BiPredicate<String, String> pr2 = String::equals;
        System.out.println(pr2.test("shiliu", "shiliu001"));
    }

    /**
     * 学生类
     */
    @Data
    static class Student {
        private Integer id;
        private String name;
    }
}

