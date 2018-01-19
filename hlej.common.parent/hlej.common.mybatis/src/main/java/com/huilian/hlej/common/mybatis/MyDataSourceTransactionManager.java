package com.huilian.hlej.common.mybatis;

import javax.sql.DataSource;

import org.springframework.transaction.TransactionDefinition;

import lombok.extern.slf4j.Slf4j;

/** 
 * 使用@Transactional时采用的数据源类型
 *  类            名:   MyDataSourceTransactionManager
 *  修 改 记 录:   //修改历史记录，包括修改日期、修改者及修改内容
 *  版 权 所 有:   版权所有(C)2017-2017
 *  公             司:  汇联金融服务控股有限公司
 *  @version  V1.0
 *  @date     2017年11月23日
 *  @author   cuiyi
 *
 */
@Slf4j
public class MyDataSourceTransactionManager extends org.springframework.jdbc.datasource.DataSourceTransactionManager {

	/**
	 * serialVersionUID:TODO
	 */
	private static final long serialVersionUID = 1L;

	public MyDataSourceTransactionManager(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) {
		// 如果已经设置了读写权限测不进行修改
		log.debug("设置数据源类型");
		if (!definition.isReadOnly()) {
			DataSourceContextHolder.write();
		} else {
			DataSourceContextHolder.read();
		}
		super.doBegin(transaction, definition);
	}

	/**
	 * 清理本地线程的数据源
	 * 
	 * @param transaction
	 */
	@Override
	protected void doCleanupAfterCompletion(Object transaction) {
		super.doCleanupAfterCompletion(transaction);
		log.debug("清理数据源类型");
		DataSourceContextHolder.clear();
	}

}
