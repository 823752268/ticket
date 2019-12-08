package com.fei.ticket.common.exception;

/**
 * Created by didi on 2017/7/10.
 */
public class BusinessException extends RuntimeException implements ApiErrors {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMessage;
	private int code;

	public BusinessException(int code, String errorCode, String errorMessage) {
		super(errorCode + ":" + errorMessage);
		this.code = code;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public BusinessException(ApiErrors errors) {
		this(errors.getCode(), errors.getErrorCode(), errors.getErrorMessage());
	}

	public BusinessException(ApiErrors errors, Object... args) {
		this(errors.getCode(), errors.getErrorCode(), String.format(errors.getErrorMessage(), args));
	}

	@Override
	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public int getCode() {
		return code;
	}
}
