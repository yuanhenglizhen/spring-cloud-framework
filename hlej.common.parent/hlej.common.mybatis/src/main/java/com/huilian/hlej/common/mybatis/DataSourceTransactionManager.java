package com.huilian.hlej.common.mybatis;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

/**
 * 初始化整备管理器，这里主要是要更改默认的DataSourceTransactionManager
 * 类 名: DataSourceTransactionManager 修 改 记 录: //修改历史记录，包括修改日期、修改者及修改内容 版 权 所 有:
 * 版权所有(C)2017-2017 公 司: 汇联金融服务控股有限公司
 * 
 * @version V1.0
 * @date 2017年11月19日
 * @author cuiyi
 *
 */
@Slf4j
@Configuration
@EnableTransactionManagement
public class DataSourceTransactionManager extends DataSourceTransactionManagerAutoConfiguration {


	/**
	 * 
	 * MyBatis自动参与到spring事务管理中，无需额外配置，只要org.mybatis.spring.SqlSessionFactoryBean引用的数据源与DataSourceTransactionManager引用的数据源�?致即可，否则事务管理会不起作用�??
	 * 
	 * @return
	 */
	@Resource(name = "writeDataSource")
	private DataSource dataSource;

	@Bean(name = "transactionManager")
	public org.springframework.jdbc.datasource.DataSourceTransactionManager transactionManagers() {
		log.info("事务管理器初始化开始");
		MyDataSourceTransactionManager dstm = new MyDataSourceTransactionManager(
				(DataSource) SpringUtils.getBean("roundRobinDataSouceProxy"));
        log.info("事务管理器初始化成功");
		return dstm;
	}
}
