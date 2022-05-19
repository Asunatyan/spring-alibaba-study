package com.example.nacosconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

public class DemoController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/findOrderByUserId/{id}")
    public String  findOrderByUserId(@PathVariable("id") Integer id) {
        // RestTemplate调用
        //String url = "http://localhost:8020/order/findOrderByUserId/"+id;
        //模拟ribbon实现
        //String url = getUri("mall-order")+"/order/findOrderByUserId/"+id;
        // 添加@LoadBalanced
        String url = "http://mall-order/order/findOrderByUserId/"+id;

        return restTemplate.getForObject(url, String.class);
    }

}
