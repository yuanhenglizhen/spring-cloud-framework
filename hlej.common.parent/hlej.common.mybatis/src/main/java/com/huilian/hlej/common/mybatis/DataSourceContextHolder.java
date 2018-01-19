package com.huilian.hlej.common.mybatis;

import lombok.extern.slf4j.Slf4j;

/** 
 * 设置线程变理为读模式或写模式
 *  类            名:   DataSourceContextHolder
 *  修 改 记 录:   //修改历史记录，包括修改日期、修改者及修改内容
 *  版 权 所 有:   版权所有(C)2017-2017
 *  公             司:  汇联金融服务控股有限公司
 *  @version  V1.0
 *  @date     2017年11月23日
 *  @author   cuiyi
 *
 */
@Slf4j
public class DataSourceContextHolder {

	
	
	private static final ThreadLocal<DataSourceType> local = new ThreadLocal<DataSourceType>();  
	  
    public static ThreadLocal<DataSourceType> getLocal() {  
        return local;  
    }  
  
    /** 
     * 读可能是多个库 
     */  
    public static void read() {  
        local.set(DataSourceType.read);  
        log.info("===============已切换数据据源到[读]模式===============");  
    }  
  
    /** 
     * 写只有一个库 
     */  
    public static void write() {  
        local.set(DataSourceType.write);  
        log.info("===============已切换数据据源到[写]模式===============");  
    }  
  
    public static DataSourceType getJdbcType() {  
        return local.get();  
    }  
    public static void SetJdbcType(DataSourceType dataSourceType) {  
    	local.set(dataSourceType);;  
    }  
    
    public static void clear() {

        log.info("==============清楚设置的数据源模式===============");  
    	local.remove();
    }

}
