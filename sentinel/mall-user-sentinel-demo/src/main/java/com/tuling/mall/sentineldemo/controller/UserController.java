package com.tuling.mall.sentineldemo.controller;


import java.util.Arrays;
import java.util.Map;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tuling.mall.sentineldemo.entity.UserEntity;
import com.tuling.mall.sentineldemo.feign.OrderFeignService;
import com.tuling.mall.sentineldemo.sentinel.CommonBlockHandler;
import com.tuling.mall.sentineldemo.sentinel.CommonFallback;
import com.tuling.mall.sentineldemo.sentinel.MyBlockExceptionHandler;
import com.tuling.mall.sentineldemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.tuling.common.utils.PageUtils;
import com.tuling.common.utils.R;


/**
 * 
 *
 * @author fox
 * @email 2763800211@qq.com
 * @date 2021-01-28 15:53:24
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    OrderFeignService orderFeignService;

    @RequestMapping(value = "/findOrderByUserId/{id}")
//    @SentinelResource(value = "findOrderByUserId",
//            blockHandlerClass = CommonBlockHandler.class,
//            blockHandler = "handleException2")
    public R  findOrderByUserId(@PathVariable("id") Integer id) {

//        try {
//            // 模拟测试并发线程数限流
//            Thread.sleep(900);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        
        // findOrderByUserId  限流规则  2    sentinel dashboard 定义规则

        //feign调用
        R result = orderFeignService.findOrderByUserId(id);

        return result;
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    @SentinelResource(value = "userlist",
            blockHandlerClass = CommonBlockHandler.class,
            blockHandler = "handleException",fallback = "fallback")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);
       // int i=1/0;

        return R.ok().put("page", page);
    }

    public R handleException(@RequestParam Map<String, Object> params,BlockException exception){
        return R.error(-1,"===被限流降级啦===");
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @SentinelResource(value = "userinfo",
            blockHandlerClass = CommonBlockHandler.class,
            blockHandler = "handleException2",
            fallbackClass = CommonFallback.class,
            fallback = "fallback"
    )
    public R info(@PathVariable("id") Integer id){
		UserEntity user = userService.getById(id);

		if(id==4){
		    throw new IllegalArgumentException("异常参数");
        }

        return R.ok().put("user", user);
    }

    public R handleException2(@PathVariable("id") Integer id, BlockException exception){
        return R.error(-1,"===被限流降级啦===");
    }
    public R fallback(@PathVariable("id") Integer id,Throwable e){
        return R.error(-1,"===被熔断降级啦==="+e.getMessage());
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UserEntity user){
		userService.save(user);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody UserEntity user){
		userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
