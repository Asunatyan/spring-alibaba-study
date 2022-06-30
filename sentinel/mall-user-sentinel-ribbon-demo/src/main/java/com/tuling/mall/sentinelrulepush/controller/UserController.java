package com.tuling.mall.sentinelrulepush.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.tuling.common.utils.R;
import com.tuling.mall.sentinelrulepush.common.ExceptionUtil;
import com.tuling.mall.sentinelrulepush.feign.OrderFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * 
 *
 * @author fox
 * @email 2763800211@qq.com
 * @date 2021-01-28 15:53:24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

//    @RequestMapping(value = "/findOrderByUserId/{id}")
////    @SentinelResource(value = "findOrderByUserId",
////        fallback = "fallback",fallbackClass = ExceptionUtil.class,
////        blockHandler = "handleException",blockHandlerClass = ExceptionUtil.class
////    )
//    public R  findOrderByUserId(@PathVariable("id") Integer id) {
//
//        //try  catch
//        //ribbon实现    ribbon
//        String url = "http://mall-order/order/findOrderByUserId/"+id;
//        R result = restTemplate.getForObject(url,R.class);
//
//        if(id==4){
//            throw new IllegalArgumentException("非法参数异常");
//        }
//
//        return result;
//    }

    @Autowired
    OrderFeignService orderFeignService;

    @RequestMapping(value = "/findOrderByUserId/{id}")
    public R  findOrderByUserId(@PathVariable("id") Integer id) {
        //feign调用
        R result = orderFeignService.findOrderByUserId(id);
        return result;
    }

}
