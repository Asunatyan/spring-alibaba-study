package com.tuling.mall.sentineldemo.sentinel;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuling.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Fox
 */
@Slf4j
public class MyUrlBlockHandler implements UrlBlockHandler {
    @Override
    public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException e) throws IOException {
        log.info("UrlBlockHandler BlockException================"+e.getRule());
        R r = null;

        if (e instanceof FlowException) {
            r = R.error(100,"接口限流了");

        } else if (e instanceof DegradeException) {
            r = R.error(101,"服务降级了");

        } else if (e instanceof ParamFlowException) {
            r = R.error(102,"热点参数限流了");

        } else if (e instanceof SystemBlockException) {
            r = R.error(103,"触发系统保护规则了");

        } else if (e instanceof AuthorityException) {
            r = R.error(104,"授权规则不通过");
        }

        //返回json数据
        response.setStatus(500);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(), r);
    }
}
