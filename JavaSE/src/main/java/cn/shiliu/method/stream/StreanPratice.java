package cn.shiliu.method.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author shiliu
 * @date 2024/5/6
 * @apiNote
 */
public class StreanPratice {
    /**
     * Stream练习
     *
     * @param args
     */
    public static void main(String[] args) {
        ////示例1: 创建一个整数流并筛选偶数
        //test1();
        ////示例2: 使用map映射操作
        //test2();
        ////示例3: 使用flatMap扁平化嵌套集合
        //test3();
        ////示例4: 使用reduce进行累积操作
        //test4();
        //示例5: 使用sorted进行过滤
        test5();
        //forEach迭代:
        test6();
        //distinct去重:
        test7();
        //
        test8();
        //
        test9();
        //
        test10();
    }

    /**
     * 示例1: 创建一个整数流并筛选偶数
     */
    private static void test1() {
        List<Integer> numbers = Arrays.asList(1, 3, 4, 5, 6, 7);
        List<Integer> collect = numbers.stream()
                //中间操作（业务处理条件)
                .filter(n -> n % 2 == 0)
                //终端操作
                .collect(Collectors.toList());
        System.out.println("创建一个整数流并筛选偶数" + collect);
    }

    /**
     * 示例2: 使用map映射操作
     * 将输入的名字全部转换成大写
     */
    private static void test2() {
        List<String> names = Arrays.asList("Alice", "Bob", "Bob", "Charlie", "David");
        //将输入的名字全部转换成大写
        List<String> collect1 = names.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println("使用map映射操作" + collect1);
        //去重
        Set<String> collected = names.stream().map(String::toUpperCase).collect(Collectors.toSet());
        System.out.println("使用map映射操作" + collected);
    }

    /**
     * 示例3: 使用flatMap扁平化嵌套集合
     * https://www.cnblogs.com/bigleft/p/18118302
     */
    private static void test3() {
        //嵌套集合
        List<List<Integer>> nestedList = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5), Arrays.asList(6, 7, 8));
        List<Integer> collect3 = nestedList.stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println("使用flatMap扁平化嵌套集合" + collect3);
        //嵌套集合
        List<List<String>> stringList = Arrays.asList(Arrays.asList("1", "2", "3"), Arrays.asList("4", "5"), Arrays.asList("6", "7", "8"));
        List<String> stringList1 = stringList.stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(stringList1);
        //扁平化嵌套集合：当需要对一个嵌套的集合进行操作时，可以使用 flatMap 将其展开为一个扁平的集合，便于进一步的处理。
        //过滤和映射：在对集合进行过滤和映射操作时，可以使用 flatMap 将每个元素转换为一个 Stream，并在转换过程中进行一些过滤操作。
        //链式操作：flatMap 方法可以与其他 Stream 方法链式调用，以实现更复杂的数据处理逻辑。
        //转换集合：flatMap方法可以用于将一个集合中的元素转换为另一个集合。
        //处理Optional对象：flatMap方法可以用于处理Optional对象的嵌套情况
    }

    /**
     * 示例4: 使用reduce进行累积操作
     * reduce 操作可以实现从Stream中生成一个值，其生成的值不是随意的，而是根据指定的计算模型。
     * 比如，之前提到count、min和max方法，因为常用而被纳入标准库中。事实上，这些方法都是reduce操作。
     */
    private static void test4() {
        //取随机数进行
        //reduce 操作可以实现从Stream中生成一个值，其生成的值不是随意的，而是根据指定的计算模型。
        //比如，之前提到count、min和max方法，因为常用而被纳入标准库中。事实上，这些方法都是reduce操作。
        int sum = IntStream.range(1, 6).reduce(0, (x, y) -> x + y);
        System.out.println(sum);
    }

    /**
     * sorted进行排序:
     */
    private static void test5() {
        List<String> fruits = Arrays.asList("apple", "date", "qanana", "zanana", "cherry", "", "fig");
        List<String> sortedFruits = fruits.stream().sorted().collect(Collectors.toList());
        System.out.println("5、Sorted fruits: " + sortedFruits);
        String s = sortedFruits.get(0);
        System.out.println(s);
    }

    /**
     * forEach迭代:
     */
    private static void test6() {
        List<Integer> numbersToPrint = Arrays.asList(10, 20, 30, 40, 50);
        System.out.print("6、Numbers to print: ");
        numbersToPrint.forEach(num -> System.out.print(num + "---122---"));
        System.out.println();

    }

    /**
     * distinct去重:
     */
    private static void test7() {
        List<Integer> duplicateNumbers = Arrays.asList(1, 2, 2, 3, 3, 4, 5, 5);
        List<Integer> distinctNumbers = duplicateNumbers.stream().distinct().collect(Collectors.toList());
        System.out.println("7、Distinct numbers: " + distinctNumbers);

    }

    /**
     * imit和skip限制结果集大小:
     * skip(x)：返回丢弃流中的前x个元素后剩下元素组成的新流；若原流中包含的元素个数小于x，则返回空流。
     * limit(x): 对一个Stream流进行截断操作，获取其前x个元素；若原流中包含的元素个数小于x，那就获取其所有的元素；
     * https://www.cnblogs.com/larrydpk/p/12078124.html
     * tips：如果Stream过大或是无限流，小心skip()会有性能问题。
     */
    private static void test8() {
        //创建一个整数列表 numbersToLimitSkip，包含从1到10的整数
        List<Integer> numbersToLimitSkip = IntStream.range(1, 11)
                .boxed().collect(Collectors.toList());
        System.out.println(numbersToLimitSkip);
        //使用流操作对列表 numbersToLimitSkip 进行处理，跳过前两个元素（即跳过1和2），
        //然后限制只获取接下来的五个元素（3, 4, 5, 6, 7）：
        List<Integer> limitedAndSkippedNumbers = numbersToLimitSkip.stream()
                .skip(2)
                .limit(5)
                .collect(Collectors.toList());
        System.out.println("8、Limited and skipped numbers: " + limitedAndSkippedNumbers);
    }

    /**
     * 使用allMatch、anyMatch和noneMatch进行谓词匹配:
     */
    private static void test9() {
        //Arrays.asList是创建一个集合，并完成一个数据的初始化赋值，new ArrayList<>（）是只能声明功能，如果需要完成初始化操作，需要使用另外一个集合来进行赋值操作。
        //List<Integer> numbersToMatch = new ArrayList<>(5, 10, 15, 20, 25);
        List<Integer> numbersToMatch = new ArrayList<>(Arrays.asList(5, 10, 15, 20, 25));
        //anyMatch-判断数据列表中全部元素都符合设置的predicate条件，如果是就返回true，否则返回false，流为空时总是返回true。
        boolean allEven = numbersToMatch.stream().allMatch(n -> n % 2 == 0);//false
        //anyMatch-判断数据列表中是否存在任意一个元素符合设置的predicate条件，如果是就返回true，否则返回false。
        boolean anyOdd = numbersToMatch.stream().anyMatch(n -> n % 2 != 0);//true
        //noneMatch-判断数据列表中全部元素都不符合设置的predicate条件，如果是就返回true，否则返回false，流为空时总是返回true。
        boolean noneGreaterThan30 = numbersToMatch.stream().noneMatch(n -> n > 30);//false
        System.out.println("9、All even: " + allEven);
        System.out.println("9、Any odd: " + anyOdd);
        System.out.println("9、None greater than 30: " + noneGreaterThan30);
    }

    /**
     * list转换为map:
     */
    private static void test10() {
        List<String> fruitsToGroup = Arrays.asList("apple", "banana", "cherry", "date", "fig");
        //使用collect进行分组
        Map<Character, List<String>> collect = fruitsToGroup.stream()
                .collect(Collectors.groupingBy(s -> s.charAt(0)));
        System.out.println("10、Grouped fruits: " + collect);
        // todo有问题-待解决
        //collect.forEach(x-> System.out.println());
    }
}
