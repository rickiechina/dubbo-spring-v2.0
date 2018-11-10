# 一、运行工具与环境



运行环境：JDK 8，Maven 3.3+

技术栈：SpringBoot 2.0+、Dubbo 2.6+、ZooKeeper 3.3+

工具：IntelliJ IDEA、谷歌浏览器



需要安装 ZooKeeper，可以到ZooKeeper官网下载。



# 二、Springboot快速集成Dubbo关键的依赖

```xml
    <dependency>
        <groupId>com.alibaba.spring.boot</groupId>
        <artifactId>dubbo-spring-boot-starter</artifactId>
        <version>2.0.0</version>
    </dependency>
```



# 三、实现Service Provider和Consumer

### 如何发布Dubbo 服务

添加如下依赖：

```yaml
<dependency>
    <groupId>com.alibaba.spring.boot</groupId>
    <artifactId>dubbo-spring-boot-starter</artifactId>
    <version>2.0.0</version>
</dependency>
```


### 在application.yaml添加dubbo的相关配置信息，样例配置如下:

```yaml
server:
  port: 9090
spring:
  application:
    name: dubbo-provider
  dubbo:
    server: true
    registry: zookeeper://127.0.0.1:2181
    protocol:
      name: dubbo
      port: 20880
```



注：这个配置只针对服务提供端，消费端不用指定协议，它自己会根据服务端的地址信息和@Reference注解去解析协议。



### 接下来在Spring Boot Application的上添加@EnableDubboConfiguration，表示要开启dubbo功能. (dubbo provider服务可以使用或者不使用web容器)

```java
@SpringBootApplication
@EnableDubboConfiguration
public class DubboProviderApplication {
  //...
}
```



### 编写你的dubbo服务，只需要添加要发布的服务实现上添加

**@Service（import com.alibaba.dubbo.config.annotation.Service）**注解，其中interfaceClass是要发布服务的接口.

```java
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

```



### 启动你的Spring Boot应用，观察控制台，可以看到dubbo启动相关信息.



### 如何消费Dubbo服务

\* 添加依赖:

```xml
    <dependency>
        <groupId>com.alibaba.spring.boot</groupId>
        <artifactId>dubbo-spring-boot-starter</artifactId>
        <version>2.0.0</version>
    </dependency>
```





### 在application.properties添加dubbo的相关配置信息，样例配置如下:

```yaml
server:
  port: 8088

## Dubbo 服务消费者配置
spring:
  dubbo:
    application:
      name: dubbo-consumer
    protocol:
      name: dubbo
      port: 20880
    registry:
      address: zookeeper://127.0.0.1:2181
```



### 开启@EnableDubboConfiguration

```java
@SpringBootApplication
@EnableDubboConfiguration
public class DubboConsumerApplication {

public static void main(String[] args){
SpringApplication.run(DubboConsumerApplication.class, args);
}
}

```

*** 通过@Reference注入需要使用的interface.**

```java
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

```



