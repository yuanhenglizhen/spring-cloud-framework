package com.huilian.hlej.common.mybatis;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import lombok.extern.slf4j.Slf4j;

 
/** 
 * 数据库路由
 *  类            名:   MyAbstractRoutingDataSource
 *  修 改 记 录:   //修改历史记录，包括修改日期、修改者及修改内容
 *  版 权 所 有:   版权所有(C)2017-2017
 *  公             司:  汇联金融服务控股有限公司
 *  @version  V1.0
 *  @date     2017年11月23日
 *  @author   cuiyi
 *
 */
@Slf4j
public class MyAbstractRoutingDataSource extends AbstractRoutingDataSource {

	private final int dataSourceNumber;
	private static final Long MAX_POOL = Long.MAX_VALUE;
	private AtomicLong count = new AtomicLong(0);
	private final Lock lock = new ReentrantLock();

	public MyAbstractRoutingDataSource(int dataSourceNumber) {
		this.dataSourceNumber = dataSourceNumber;
	}

	@Override
	protected Object determineCurrentLookupKey() {
		DataSourceType dataSourceType = DataSourceContextHolder.getJdbcType();

		if (dataSourceType == null) {
			log.debug("根据数据源模式选择数据源,数据源模式不存在，默认为：" + DataSourceType.write.getName());
			return DataSourceType.write.getType();
		} 

		log.debug("根据数据源模式选择数据源,数据源为：" + dataSourceType.getName());
		if (dataSourceType.equals(DataSourceType.write)) {
			return DataSourceType.write.getType();
		}

		// 读 简单负载均衡
		long number = count.getAndAdd(1);
		if ((number + 1) >= MAX_POOL) {
			try {
				lock.lock();
				if ((number + 1) >= MAX_POOL) {
					count.set(0l);
				}

			} finally {
				lock.unlock();
			}
		}
		int lookupKey = (int) number % dataSourceNumber;
		return new Integer(lookupKey);
	}

}
