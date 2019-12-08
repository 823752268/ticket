package com.fei.ticket.service;

import com.fei.ticket.common.BaseResponse;

import java.util.List;

public interface IService {

    BaseResponse<List<String>> getList();

    BaseResponse<String> getLoginImg();

}
