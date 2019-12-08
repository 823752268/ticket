package com.fei.ticket.web;

import com.fei.ticket.common.BaseResponse;
import com.fei.ticket.service.IService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
public class Controller {

    @Resource
    IService service;

    @GetMapping("getList")
    public BaseResponse<List<String>> getList(){
        BaseResponse<List<String>> response = service.getList();
        return response;
    }


}
