package com.tuling.mall.sentinelrulepush.feign;

import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.tuling.common.utils.R;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Fox
 */
@Component
public class FallbackOrderFeignServiceFactory implements FallbackFactory<OrderFeignService> {
    @Override
    public OrderFeignService create(Throwable throwable) {

        return new OrderFeignService() {
            @Override
            public R findOrderByUserId(Integer userId) {
    
                if (throwable instanceof FlowException) {
                    return R.error(100,"接口限流了");
                }
                
                return R.error(-1,"=======服务降级了========");
            }
        };
    }
}
