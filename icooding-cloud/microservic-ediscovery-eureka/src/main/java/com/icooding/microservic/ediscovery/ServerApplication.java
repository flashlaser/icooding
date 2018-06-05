package com.icooding.microservic.ediscovery;

/**
 * project_name icooding-cloud
 * class eureka
 * date  2017/11/21
 * author ibm
 * version 1.0
 */
@EnableEurekaServer  //启动一个服务注册中心提供给其他应用进行对话
@SpringBootApplication
public class ServerApplication  {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
