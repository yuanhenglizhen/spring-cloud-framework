package com.huilian.hlej.mongodb.thread;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskVO {
	/**
	 * bizId:业务ID
	 */
	private String bizId;
	/**
	 * bizType:业务类型，如：订单ID，用户ID
	 */
	private String bizType;
	/**
	 * bizData:业务数据 
	 */
	private String bizData;
	/**
	 * url:类名/方法名称
	 */
	private String url;
	/**
	 * createTime:创建时间
	 */
	private Date createTime;
}
