package com.example.demo.o1;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
//@ConditionalOnResource(resources = "cccc.xml")
@ConfigurationProperties(prefix = "auto")
@Data
public class Demo1 {

    private String username;
    private String age;
    private Integer sex;


    public static void main(String[] args) {
        Predicate<Integer> gt = s -> s > 5;
        Predicate<Integer> lt = s -> s < 5;

        Predicate<Integer> multipleOf_5 = s -> s % 5 == 0;
        Predicate<Integer> multipleOf_2 = s -> s % 2 == 0;

        System.out.println("10 大于 5: " + gt.test(10));
        System.out.println("10 小于 5: " + lt.test(10));
        System.out.println("20 大于 5，且是5的倍数: " + gt.and(multipleOf_5).test(20));
        System.out.println("8 是 5 或 2 的倍数: " + multipleOf_5.or(multipleOf_2).test(8));
        System.out.println("GET 等于 get: " + Predicate.isEqual("get").test("GET"));
        System.out.println("取 10 大于5 的反" + gt.negate().test(10));

        List<String> list = Arrays.asList("java", "js", "python", "vue", "scala");
        List<String> stringList = filter(list, s -> s.contains("j") || s.contains("a"));
        System.out.println(stringList);
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> action) {
        ArrayList<T> arrayList = new ArrayList<>();
        list.forEach(item -> {
            if (action.test(item)) {
                arrayList.add(item);
            }
        });
        return arrayList;
    }
}
