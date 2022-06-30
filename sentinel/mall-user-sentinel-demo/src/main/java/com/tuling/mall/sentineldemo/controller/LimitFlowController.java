package com.tuling.mall.sentineldemo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tuling.mall.sentineldemo.entity.UserEntity;
import com.tuling.mall.sentineldemo.sentinel.CommonBlockHandler;
import com.tuling.mall.sentineldemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Fox
 */
@RestController
public class LimitFlowController {

    @RequestMapping("/test")
//    @SentinelResource(value = "test",blockHandlerClass = CommonBlockHandler.class,blockHandler = "handleException3")
    public String test() {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "========test()========";
    }

    AtomicInteger atomicInteger = new AtomicInteger();

    @RequestMapping("/test2")
    public String test2() {
        atomicInteger.getAndIncrement();
        if (atomicInteger.get() % 2 == 0){
            //模拟异常和异常比率
            int i = 1/0;
        }

        return "========test2()========";
    }

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/test3")  //CommonFilter
    public UserEntity test3() {
        UserEntity user = userService.getUser(1);
        return user;
    }

    @RequestMapping(value = "/test4")
    public UserEntity test4() {
        UserEntity user = userService.getUser(1);
        return user;
    }


    public String handleException(BlockException exception) {
        return "===被限流降级啦===";
    }
}
