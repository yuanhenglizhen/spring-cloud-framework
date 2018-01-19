package com.huilian.hlej.common.core.base;

/**
 *  公共返回码定义
 *  类            名:   ReturnCode
 *  修 改 记 录:   //修改历史记录，包括修改日期、修改者及修改内容
 *  版 权 所 有:   版权所有(C)2017-2017
 *  公             司:  汇联金融服务控股有限公司
 *  @version  V1.0
 *  @date     2017年11月6日
 *  @author   antizen
 */
public enum ReturnCode {
	

	SUCCESS("00000000","成功"),
	SUCCESS_PART_SUCCESS("00000001","部分数据成功"),
	ERROR("00001000","失败"),
	ERROR_SYSTEM_ERROR("00001001","系统异常"),
	ERROR_INVOKE_SERVICE_ERROR("00001002","调用服务系统");
	private ReturnCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	/**返回码*/
	private String code;
	/**返回消息*/
	private String msg;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
