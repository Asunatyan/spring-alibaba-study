package com.example.demo.o1;

import java.net.URL;

public class GetResourceTest {
    public static void main(String[] args) {
        // 获取不到, 因为是相对于xxx/target/classes/com/resourcetest/的路径
        URL resource1 = GetResourceTest.class.getResource("config.xml");
        // 下面3个能获取到, 因为是相对于classpath的路径
        URL resource2 = GetResourceTest.class.getResource("/config.xml");
        URL resource3 = GetResourceTest.class.getClassLoader().getResource("config.xml");
        URL resource4 = GetResourceTest.class.getClassLoader().getResource("./config.xml");
        // 获取不到，无法被解析为相对于classpath的路径
        URL resource5 = GetResourceTest.class.getClassLoader().getResource("/config.xml");

        System.out.println(resource1); // null
        System.out.println(resource2); // 非null
        System.out.println(resource3); // 非null
        System.out.println(resource4); // 非null
        System.out.println(resource5); // null
    }
}
