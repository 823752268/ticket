package com.fei.ticket.service;

import com.fei.ticket.common.BaseResponse;
import com.fei.ticket.common.request.GetTicketListRequest;
import com.fei.ticket.common.request.SubRequest;

import java.util.List;

public interface IService {

    BaseResponse<List<String>> getTicketList(GetTicketListRequest request);

    BaseResponse<String> sub1(SubRequest request);

}
