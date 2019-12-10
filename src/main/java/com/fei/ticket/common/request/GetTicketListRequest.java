package com.fei.ticket.common.request;

import lombok.Data;

@Data
public class GetTicketListRequest {

    private String date;

    private String begin_sta;
    private String end_sta;

}
