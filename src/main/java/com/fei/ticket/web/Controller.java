package com.fei.ticket.web;

import com.fei.ticket.common.BaseResponse;
import com.fei.ticket.common.request.GetTicketListRequest;
import com.fei.ticket.common.request.SubRequest;
import com.fei.ticket.service.IService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
public class Controller {

    @Resource
    IService service;

    @GetMapping("getTicketList")
    public BaseResponse<List<String>> getTicketList(GetTicketListRequest request){
        BaseResponse<List<String>> response = service.getTicketList(request);
        return response;
    }

    @PostMapping("sub1")
    public BaseResponse<String> sub1(@RequestBody SubRequest request){
        BaseResponse<String> response = service.sub1(request);
        return response;
    }


}
