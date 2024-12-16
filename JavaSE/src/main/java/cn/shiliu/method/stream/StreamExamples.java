package cn.shiliu.method.stream;


import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author shiliu
 * @date 2024/4/30
 * @apiNote Stream的综合操作
 */
public class StreamExamples {
    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person("Alice", 25, "New York"),
                new Person("Bob", 30, "Los Angeles"),
                new Person("Charlie", 35, "Chicago"),
                new Person("David", 25, "New York"),
                new Person("Eve", 28, "Los Angeles"),
                new Person("Frank", 40, "Chicago"),
                new Person("Grace", 30, "New York"),
                new Person("Helen", 35, "Chicago"),
                new Person("Ivy", 28, "Los Angeles")
        );
        //同时进行扁平化，过滤，映射，排序，去重，限制和分组操作
        Map<String, List<String>> citiesAndNames = people.stream()
                .flatMap(person -> Stream.of(person.getName() + "-" + person.getCity()))
                .filter(fullName -> fullName.length() < +20)//过滤长度小于等于20的姓名和城市组合
                .sorted() // 按字母顺序排序
                .distinct() // 去重
                .limit(5) // 限制输出结果
                .collect(Collectors.groupingBy(fullName -> fullName.split("-")[1]));
        System.out.println(citiesAndNames);
        //遍历输出结果
        citiesAndNames.forEach((city, names) -> {
            System.out.println("City: " + city);
            System.out.println("Names: " + names);
        });
    }

    //输出结果
    //{New York=[Alice-New York, David-New York], Chicago=[Charlie-Chicago], Los Angeles=[Bob-Los Angeles, Eve-Los Angeles]}
    //City: New York
    //Names: [Alice-New York, David-New York]
    //City: Chicago
    //Names: [Charlie-Chicago]
    //City: Los Angeles
    //Names: [Bob-Los Angeles, Eve-Los Angeles]

    /**
     * 输出参数
     */
    @Data
    private static class Person {
        private String name;
        private int age;
        private String city;

        /**
         * 字段的构造方法
         *
         * @param name
         * @param age
         * @param city
         */
        public Person(String name, int age, String city) {
            this.name = name;
            this.age = age;
            this.city = city;
        }
    }

}
