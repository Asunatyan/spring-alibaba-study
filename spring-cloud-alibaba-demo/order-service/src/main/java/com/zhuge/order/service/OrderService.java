package com.zhuge.order.service;

import com.zhuge.order.feign.CreditService;
import com.zhuge.order.feign.StockService;
import com.zhuge.order.feign.WmsService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private StockService stockService;
    @Autowired
    private CreditService creditService;
    @Autowired
    private WmsService wmsService;

    @GlobalTransactional
    @Transactional
    public String createOrder(Long productId, Long userId, Integer stockCount, Integer creditCount) {
        System.out.println("创建订单成功");  //TODO
        stockService.deductStock(productId, stockCount);
        creditService.addCredit(userId, creditCount);
        wmsService.delivery(userId, productId);
        return "success";
    }

}