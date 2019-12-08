package com.fei.ticket.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fei.ticket.common.exception.ApiErrors;
import com.fei.ticket.common.exception.BusinessException;

public class BaseResponse<T> {
	private String errorCode;
	private String errorMessage;
	private int status;
	private T data;

	private String extMessage;

	public String getExtMessage() {
		return extMessage;
	}

	public void setExtMessage(String extMessage) {
		this.extMessage = extMessage;
	}

	public BaseResponse() {
	}

	public BaseResponse(BusinessException e) {
		this(e.getCode(), e.getErrorCode(), e.getErrorMessage());
	}

	public BaseResponse(ApiErrors errors) {
		this(errors.getCode(), errors.getErrorCode(), errors.getErrorMessage());
	}

	public BaseResponse(int status, String errorCode, String errorMessage) {
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@JsonProperty("success")
	public boolean isSuccess() {
		return (errorCode == null && errorMessage == null) || ("SUCCESS".equals(errorCode) && "请求成功".equals(errorMessage));
	}
}
