package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.api.DemoServiceV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by libotao on 2018/11/7.
 */
@Service(version="1.0.0", timeout = 1000, interfaceClass = DemoServiceV2.class)
@Component
public class DemoServiceImpl implements DemoServiceV2 {

    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public String sayHello(String s) {

        logger.info("Calling sayHello() method ...");

        return "Service provider: Hello, " + s;
    }
}
