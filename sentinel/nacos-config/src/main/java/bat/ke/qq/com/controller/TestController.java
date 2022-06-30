package bat.ke.qq.com.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope  // 替换配置bean
public class TestController{

    @Value("${common.age}")
     String age;
    @Value("${common.name}")
     String name;

    @GetMapping("/common")
    public String hello() {
        return name+","+age;
    }
    
    
   
}