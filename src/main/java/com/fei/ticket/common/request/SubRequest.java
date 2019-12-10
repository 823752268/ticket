package com.fei.ticket.common.request;

import lombok.Getter;
import lombok.Setter;


public class SubRequest {

    private String train_date_long;  //Wed Jan 08 2020 00:00:00 GMT+0800 (中国标准时间)
    private String train_no;
    private String stationTrainCode;
    private String seatType="O";
    private String fromStationTelecode;
    private String toStationTelecode;
    private String fromStationName;
    private String toStationName;
    private String detailStr;
    private String train_date_short;  //2020-01-08


    public String getTrain_date_long() {
        return train_date_long;
    }

    public void setTrain_date_long(String train_date_long) {
        this.train_date_long = train_date_long;
    }

    public String getTrain_no() {
        return train_no;
    }

    public void setTrain_no(String train_no) {
        this.train_no = train_no;
    }

    public String getStationTrainCode() {
        return stationTrainCode;
    }

    public void setStationTrainCode(String stationTrainCode) {
        this.stationTrainCode = stationTrainCode;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getFromStationTelecode() {
        return fromStationTelecode;
    }

    public void setFromStationTelecode(String fromStationTelecode) {
        this.fromStationTelecode = fromStationTelecode;
    }

    public String getToStationTelecode() {
        return toStationTelecode;
    }

    public void setToStationTelecode(String toStationTelecode) {
        this.toStationTelecode = toStationTelecode;
    }

    public String getDetailStr() {
        return detailStr;
    }

    public void setDetailStr(String detailStr) {
        this.detailStr = detailStr;
    }

    public String getTrain_date_short() {
        return train_date_short;
    }

    public void setTrain_date_short(String train_date_short) {
        this.train_date_short = train_date_short;
    }

    public String getFromStationName() {
        return fromStationName;
    }

    public void setFromStationName(String fromStationName) {
        this.fromStationName = fromStationName;
    }

    public String getToStationName() {
        return toStationName;
    }

    public void setToStationName(String toStationName) {
        this.toStationName = toStationName;
    }
}
