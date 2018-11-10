package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.api.DemoServiceV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by libotao on 2018/11/7.
 */
@RestController
public class DubboTestController {

    @Reference(version = "1.0.0", interfaceClass = DemoServiceV2.class)
    private DemoServiceV2 demoService;

    @GetMapping("/hello/{name}")
    public String Hello(@PathVariable("name") String s){
        String ret_msg = "empty string...";

        if(demoService != null)
            ret_msg = demoService.sayHello(s);
        else{
            System.out.println("Dubbo service is null.");
        }

        return ret_msg;
    }
}
