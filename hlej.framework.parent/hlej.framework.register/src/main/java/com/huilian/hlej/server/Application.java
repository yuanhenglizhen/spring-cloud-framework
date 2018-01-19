package com.huilian.hlej.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 *  Eureka注册中心服务
 *  类            名:   EurekaServerApplication
 *  修 改 记 录:   //修改历史记录，包括修改日期、修改者及修改内容
 *  版 权 所 有:   版权所有(C)2017-2017
 *  公             司:  汇联金融服务控股有限公司
 *  @version  V1.0
 *  @date     2017年10月27日
 *  @author   antizen
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class Application {

	public static void main(String[] args) {
//		log.info("=========注册中心服务启动开始=========");
		SpringApplication.run(Application.class, args);
//		log.info("=========注册中心服务启动成功=========");
	}
}
