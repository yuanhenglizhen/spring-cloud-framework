package com.huilian.hlej.common.mybatis;

/** 
 * 数据源类型
 *  类            名:   DataSourceType
 *  修 改 记 录:   //修改历史记录，包括修改日期、修改者及修改内容
 *  版 权 所 有:   版权所有(C)2017-2017
 *  公             司:  汇联金融服务控股有限公司
 *  @version  V1.0
 *  @date     2017年11月23日
 *  @author   cuiyi
 *
 */
public enum DataSourceType {


	read("read", "从库"),  
    write("write", "主库");   
    private String type;   
    private String name;  
  
    
    public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	DataSourceType(String type, String name) {  
        this.type = type;  
        this.name = name;  
    }  

}
