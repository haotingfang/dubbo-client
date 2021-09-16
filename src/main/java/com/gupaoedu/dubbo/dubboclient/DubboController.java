package com.gupaoedu.dubbo.dubboclient;

import com.gupaoedu.dubbo.ISayHelloService;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/21-20:42
 */
@RestController
public class DubboController {
    //Dubbo提供的注解
//    @Reference(loadbalance = "roundrobin",timeout = 1,cluster ="failfast",mock = "com.gupaoedu.dubbo.dubboclient.SayHelloServiceMock",check = false)
    @Reference(loadbalance = "roundrobin",cluster ="failfast",mock = "com.gupaoedu.dubbo.dubboclient.SayHelloServiceMock",check = false,
            methods = {@Method(name = "sayHello", async = true )})
    ISayHelloService sayHelloService; //dubbo://

    @GetMapping("/sayhello")
    public String sayHello() throws InterruptedException, ExecutionException {
        String result = sayHelloService.sayHello(); //我调用这个服务可能失败，如果失败了，我要怎么处理
        System.out.println("-------1-------"+result);
        Future<String> future = RpcContext.getContext().getFuture();// 业务线程可以开始做其他事情
        result = future.get(); // 阻塞需要获取异步结果时，也可以使用 get(timeout, unit) 设置超时时间
        System.out.println("-------2-------"+result);
        return result;
    }



}
