package com.fei.ticket.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fei.ticket.common.BaseResponse;
import com.fei.ticket.common.request.GetTicketListRequest;
import com.fei.ticket.common.request.SubRequest;
import com.fei.ticket.common.util.HttpUtil;
import com.fei.ticket.common.util.StaUtil;
import com.fei.ticket.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import sun.plugin2.os.windows.Windows;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ServiceImpl implements IService {

    static String subUrl="https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest";
    static String docUrl="https://kyfw.12306.cn/otn/confirmPassenger/initDc";
    static String getUserurl="https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs";
    static String checkOrderUrl="https://kyfw.12306.cn/otn/confirmPassenger/checkOrderInfo";
    static String getQueueCountUrl="https://kyfw.12306.cn/otn/confirmPassenger/getQueueCount";
    static String confirmUrl="https://kyfw.12306.cn/otn/confirmPassenger/confirmSingleForQueue";
    static String useInfo1="O,0,1,杨晓飞,1,4104***********011,13588200025,N,f8f1cce2f52df322ab41bd39052f6849b0a0cf1009f8ed97d61d21b1822636d69fe255753290b9a5feb646b5eb082827";
    static String useInfo2="杨晓飞,1,4104***********011,1_";
    String who="";
    Map<String,String> ticketDetailMap=new HashMap<>();
    String repatToken="";
    JSONObject jsonObject=null;
    String[] detailArr=null;


    @Override
    public BaseResponse<List<String>> getTicketList(GetTicketListRequest request) {
        String uri="https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="+request.getDate()+"&leftTicketDTO.from_station="+request.getBegin_sta()+"&leftTicketDTO.to_station="+request.getEnd_sta()+"&purpose_codes=ADULT";

        BaseResponse<List<String>> baseResponse=new BaseResponse<>();
        List<String> list=new ArrayList<>();
        String s= HttpUtil.get(uri);
        if(s!=null){
            JSONObject jsonObject = JSONObject.parseObject(s);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray jsonArray = data.getJSONArray("result");
            list = jsonArray.toJavaList(String.class);
        }
        baseResponse.setData(list);
        return baseResponse;
    }

    @Override
    public BaseResponse<String> sub1(SubRequest request) {
        detailArr= request.getDetailStr().split("\\|");
        Boolean subFlag = submitOrder(request);
        if(!subFlag){
            return null;
        }
        Boolean docFlag = getDoc();
        if(!docFlag){
            return null;
        }
        if(StringUtils.isNotBlank(who)){
            getUser();
        }
        checkOrderInfo(null);
//        getQueueCount();
        confirm();
        return null;
    }

    void getTicketList2(){
        String uri="https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date=2020-01-08&leftTicketDTO.from_station=HZH&leftTicketDTO.to_station=ZZF&purpose_codes=ADULT";

        List<String> list=new ArrayList<>();
        while(true){
            String s= HttpUtil.get(uri);
            if(s!=null){
                JSONObject jsonObject = JSONObject.parseObject(s);
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray jsonArray = data.getJSONArray("result");
                list = jsonArray.toJavaList(String.class);
            }
            if(list.size()>0){
                break;
            }
            try {
                Thread.sleep(5000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        String valueStr = list.stream().filter(o -> {
            String[] arr = o.split("\\|");
            if (arr[3].equals("G1874")) {
                return true;
            }
            return false;
        }).findFirst().get();
//        submitOrder(valueStr);
        getDoc();
        getUser();
        checkOrderInfo(null);
        getQueueCount();
        confirm();
    }

    Boolean submitOrder(SubRequest request){
        try {
            LocalDate now = LocalDate.now();
            Map<String,String> map=new HashMap<>();
            String decode = URLDecoder.decode(detailArr[0], "UTF-8");
            map.put("secretStr",decode);
            map.put("train_date",request.getTrain_date_short());
            map.put("back_train_date",now.getYear()+"-"+now.getMonthValue()+"-"+now.getDayOfMonth()); //单程票 此为当前查询日期
            map.put("tour_flag","dc");
            map.put("purpose_codes","ADULT");
            map.put("query_from_station_name", request.getFromStationName());
            map.put("query_to_station_name",request.getToStationName());
            String post = HttpUtil.post(subUrl, map);
            if(StringUtils.isBlank(post)){
                log.info("预提交订单失败:信息为空 cookie可能已过期或ip已被限制");
                return false;
            }
            JSONObject jsonObject = JSONObject.parseObject(post);
            Boolean status = jsonObject.getBoolean("status");
            log.info("预提交订单结果为:{}",jsonObject.getString("messages"));
            return status;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    Boolean getDoc(){

        try {
            Map<String,String> map=new HashMap<>();
//            map.put("secretStr",decode);
            String s = HttpUtil.post(docUrl, map);
            Pattern p = Pattern.compile("globalRepeatSubmitToken = '(.*)';");
            Matcher m = p.matcher(s);
            if(m.find()){
                repatToken=m.group(1);
                System.out.println("token为:"+repatToken);
            }
            Pattern p2 = Pattern.compile("var ticketInfoForPassengerForm=(.*);");
            Matcher m2 = p2.matcher(s);
            if(m2.find()){
                String group = m2.group(1);
                String replace = group.replace("'", "\"");
                jsonObject = JSONObject.parseObject(replace);
            }
            if(StringUtils.isNotBlank(repatToken)){
                log.info("获取doc成功");
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("获取doc失败");
        return false;
    }

    void getUser(){
        Map<String,String> map=new HashMap<>();
        map.put("REPEAT_SUBMIT_TOKEN",repatToken);
        String post = HttpUtil.post(getUserurl, map);
        JSONObject jsonObject = JSONObject.parseObject(post);
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("normal_passengers");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String passenger_name = object.getString("passenger_name");
            String passenger_id_no=object.getString("passenger_id_no");
            String mobile_no=object.getString("mobile_no");
            String allEncStr=object.getString("allEncStr");
            if(passenger_name.equals(who)){
                useInfo1="O,0,1,"+passenger_name+",1,"+passenger_id_no+","+mobile_no+",N,"+allEncStr;
                useInfo2=passenger_name+",1,"+passenger_id_no+",1_";
                break;
            }
        }
        log.info("获取客结果:{}",jsonArray.toString());
    }

    void checkOrderInfo(JSONObject user){
        Map<String,String> map=new HashMap<>();
        map.put("cancel_flag","2");
        map.put("bed_level_order_num","000000000000000000000000000000");
        map.put("passengerTicketStr",useInfo1);
        map.put("oldPassengerStr",useInfo2);
        map.put("tour_flag","dc");
        map.put("randCode","");
        map.put("whatsSelect","1");
        map.put("_json_att","");
        map.put("REPEAT_SUBMIT_TOKEN",repatToken);
        try {
            String post = HttpUtil.post(checkOrderUrl, map);
            if(StringUtils.isNotBlank(post)){
                JSONObject jsonObject = JSONObject.parseObject(post);
                Boolean aBoolean = jsonObject.getJSONObject("data").getBoolean("submitStatus");
                if(aBoolean==true){
                    log.info("检查订单信息为:{}",post);
                }
            }
        }catch (Exception e){

        }

    }


    void getQueueCount(){
        Map<String,String> map=new HashMap<>();
        map.put("train_date","Wed Jan 08 2020 00:00:00 GMT+0800 (中国标准时间)");
        map.put("train_no","56000G187450");
        map.put("stationTrainCode","G1874");
        map.put("seatType","O");
        map.put("fromStationTelecode","HGH");
        map.put("toStationTelecode","ZAF");
        map.put("leftTicket",jsonObject.getString("leftTicketStr"));
        map.put("purpose_codes","00");
        map.put("train_location","H3");
        map.put("_json_att","");
        map.put("REPEAT_SUBMIT_TOKEN",repatToken);
        String post = HttpUtil.post(getQueueCountUrl, map);
        System.out.println("获取排队结果为"+post);
    }

    void confirm(){
        Map<String,String> map=new HashMap<>();
        map.put("passengerTicketStr",useInfo1);
        map.put("oldPassengerStr",useInfo2);
        map.put("randCode","");
        map.put("purpose_codes",jsonObject.getString("purpose_codes"));
        map.put("key_check_isChange",jsonObject.getString("key_check_isChange"));
        map.put("leftTicketStr",jsonObject.getString("leftTicketStr"));
        map.put("train_location",jsonObject.getString("train_location"));
        map.put("choose_seats","");
        map.put("seatDetailType","000");
        map.put("whatsSelect","1");
        map.put("roomType","00");
        map.put("dwAll","N");
        map.put("_json_att","");
        map.put("REPEAT_SUBMIT_TOKEN",repatToken);
        String post = HttpUtil.post(confirmUrl, map);
        log.info("提交订单结果为:{}",post);
    }


    public BaseResponse<String> getLoginImg() {
        String uri="https://kyfw.12306.cn/passport/captcha/captcha-image64?login_site=E&module=login&rand=sjrand&1575794316003&callback=jQuery1910532979658339602_1575794312013&_=1575794312014";
        return null;
    }
}
