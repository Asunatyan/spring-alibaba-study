package com.example.demo.o2.condition;

import com.example.demo.o1.Demo1;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnResource(resources = "${my.config}")
@ConditionalOnBean(Demo1.class)
public class OnResourceConditionTest {

    public OnResourceConditionTest() {
        System.out.println("存在........");
    }
}
