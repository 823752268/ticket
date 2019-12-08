package com.fei.ticket.common.exception;

/**
* @Description:    基础错误类
* @Author:         zhangy
* @CreateDate:     2018/9/11 下午1:49
* @UpdateUser:     zhangy
* @UpdateDate:     2018/9/11 下午1:49
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public interface ApiErrors {
	int getCode();

	String getErrorCode();

	String getErrorMessage();

}
