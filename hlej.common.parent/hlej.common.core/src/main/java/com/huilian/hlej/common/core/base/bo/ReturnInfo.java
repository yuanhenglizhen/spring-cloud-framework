package com.huilian.hlej.common.core.base.bo;

import com.huilian.hlej.common.core.base.ReturnCode;
import com.huilian.hlej.common.core.base.vo.Response;

public class ReturnInfo<T> {

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
	public static <T> ReturnInfo<T> success(T retData) {
		ReturnInfo<T> returnInfo = new ReturnInfo<T>(ReturnCode.SUCCESS.getCode());
		returnInfo.setRetData(retData);
		return returnInfo;
	}

	/**
	 * 返回默认成功对象
	 * 
	 * @return
	 */
	public static <T> ReturnInfo<T> success() {
		ReturnInfo<T> returnInfo = new ReturnInfo<T>(ReturnCode.SUCCESS.getCode());
		return returnInfo;
	}

	/**
	 * 返回默认失败对触景伤情
	 * 
	 * @param retData
	 * @return
	 */
	public static <T> ReturnInfo<T> failed(String retCode, String retMsg) {
		ReturnInfo<T> returnInfo = new ReturnInfo<T>(retCode,retMsg);
		return returnInfo;
	}

	/**
	 * 将returninfo转换成Response
	 * @return
	 */
	public <E> Response<E> converToResponse() {
		Response<E> response = new Response<E>(this.retCode, this.retMsg);
		
		return response;

	}

	/**
	 * 返回默认的失败对象
	 * 
	 * @return
	 */
	public static <T> ReturnInfo<T> failed() {
		ReturnInfo<T> returnInfo = new ReturnInfo<T>(ReturnCode.ERROR_SYSTEM_ERROR.getCode());
		return returnInfo;
	}

	public ReturnInfo() {
	}

	public ReturnInfo(String retCode) {
		this.retCode = retCode;
	}

	public ReturnInfo(String retCode, String retMsg) {
		this.retCode = retCode;
		this.retMsg = retMsg;
	}

	public ReturnInfo(String retCode, String retMsg, T retData) {
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
		// sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("retCode = ").append(this.retCode);
		sb.append(", retMsg=").append(this.retMsg);
		sb.append("]");
		return sb.toString();
	}

}
