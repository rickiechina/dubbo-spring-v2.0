package com.example;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

/**
 * Created by libotao on 2018/11/7.
 */
@SpringBootApplication
@EnableDubboConfiguration
public class DubboProviderApplication {
    // 使用jar 方式打包的启动方式
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException{
        SpringApplication.run(DubboProviderApplication.class, args).registerShutdownHook();
        countDownLatch.await();
    }
}
