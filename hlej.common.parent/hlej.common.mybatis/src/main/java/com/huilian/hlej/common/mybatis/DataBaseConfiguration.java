package com.huilian.hlej.common.mybatis;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import lombok.extern.slf4j.Slf4j;
/** 
 * 初始华数据源
 * 原理参考 http://www.jianshu.com/p/2222257f96d3
 *  类            名:   DataBaseConfiguration
 *  修 改 记 录:   //修改历史记录，包括修改日期、修改者及修改内容
 *  版 权 所 有:   版权所有(C)2017-2017
 *  公             司:  汇联金融服务控股有限公司
 *  @version  V1.0
 *  @date     2017年11月23日
 *  @author   cuiyi
 *
 */
@Slf4j
@Configuration
@PropertySource("classpath:mybatis.properties")
public class DataBaseConfiguration {

	@Value("${spring.datasource.type}")
	private Class<? extends DataSource> dataSourceType;

	@Bean(name = "writeDataSource", destroyMethod = "close", initMethod = "init")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource writeDataSource() {
		log.info("初始化写数据源开始");
		DataSource ds = DataSourceBuilder.create().type(dataSourceType).build();
		log.info("初始化写数据源成功:" + ds);
		return ds;
	}

	@Bean(name = "readDataSource1")
	@ConfigurationProperties(prefix = "spring.slave")
	public DataSource readDataSourceOne() {
		log.info("初始化读数据源(1)开始");
		DataSource ds =  DataSourceBuilder.create().type(dataSourceType).build();
		log.info("初始化读数据源(1)成功:" + ds);
		return ds;
	}

	@Bean(name = "readDataSource2")
	@ConfigurationProperties(prefix = "spring.read2")
	public DataSource readDataSourceTwo() {
		log.info("初始化读数据源(2)开始");
		DataSource ds =  DataSourceBuilder.create().type(dataSourceType).build();
		log.info("初始化读数据源(2)成功:" + ds);
		return ds;
	}

	@Bean(name = "readDataSources")
	public List<DataSource> readDataSources() {
		log.info("初始化读数据源开始");
		List<DataSource> dataSources = new ArrayList<>();
		dataSources.add(readDataSourceOne());
		dataSources.add(readDataSourceTwo());
		log.info("初始化读数据源成功");
		return dataSources;
	}
}
