package com.fei.ticket.common.request;

import lombok.Data;


public class GetTicketListRequest {

    private String date;

    private String begin_sta;
    private String end_sta;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBegin_sta() {
        return begin_sta;
    }

    public void setBegin_sta(String begin_sta) {
        this.begin_sta = begin_sta;
    }

    public String getEnd_sta() {
        return end_sta;
    }

    public void setEnd_sta(String end_sta) {
        this.end_sta = end_sta;
    }
}
