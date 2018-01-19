package com.huilian.hlej.common.core.base.vo;

import com.huilian.hlej.common.core.base.ReturnCode;

/**
 * Web请求统一返回参数
 * 
 * @author Administrator
 *
 */

public class Response<T> {
	/** 响应code */
	// private String retCode = ReturnCode.SUCCESS;
	private String retCode;
	/** 响应消息 */
	private String retMsg;
	/** 响应数据 */
	private T retData;

	/**
	 * 返回默认的成功对象
	 * 
	 * @param retData
	 * @return
	 */
	public static <T> Response<T> success(T retData) {
		Response<T> response = new Response<T>(ReturnCode.SUCCESS.getCode());
		response.setRetData(retData);
		return response;
	}

	/**
	 * 返回默认成功对象
	 * 
	 * @return
	 */
	public static <T> Response<T> success() {
		Response<T> response = new Response<T>(ReturnCode.SUCCESS.getCode());
		return response;
	}

	/**
	 * 返回默认失败对触景伤情
	 * 
	 * @param retData
	 * @return
	 */
	public static <T> Response<T> failed(T retData) {
		Response<T> response = new Response<T>(ReturnCode.ERROR_SYSTEM_ERROR.getCode());
		response.setRetData(retData);
		return response;
	}

	/**
	 * 返回默认的失败对象
	 * 
	 * @return
	 */
	public static <T> Response<T> failed() {
		Response<T> response = new Response<T>(ReturnCode.ERROR_SYSTEM_ERROR.getCode());
		return response;
	}

	public Response() {
	}

	public Response(String retCode) {
		this.retCode = retCode;
	}

	public Response(String retCode, String retMsg) {
		this.retCode = retCode;
		this.retMsg = retMsg;
	}
	public Response(String retCode, String retMsg, T retData) {
		this.retCode = retCode;
		this.retMsg = retMsg;
		this.retData = retData;
	}
	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public T getRetData() {
		return retData;
	}

	public void setRetData(T retData) {
		this.retData = retData;
	}

	public String baseReturnInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("retCode = ").append(this.retCode);
		sb.append(", retMsg=").append(this.retMsg);
		sb.append("]");
		return sb.toString();
	}

}
