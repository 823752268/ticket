package com.fei.ticket.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fei.ticket.common.BaseResponse;
import com.fei.ticket.common.util.HttpUtil;
import com.fei.ticket.service.IService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ServiceImpl implements IService {

    static String initUrl = "https://kyfw.12306.cn/otn/login/init";
    static String getJsUrl = "https://kyfw.12306.cn/otn/HttpZF/GetJS";
    static String getYzmUrl = "https://kyfw.12306.cn/passport/captcha/captcha-image64?";
    static String checkYzmUrl = "https://kyfw.12306.cn/passport/captcha/captcha-check?";
    static String loginUrl = "https://kyfw.12306.cn/passport/web/login";
    String cookie="SESSIONID=510194B1FC58F7906F65B5EFB0EE3D4C; BIGipServerpool_passport=283968010.50215.0000; route=c5c62a339e7744272a54643b3be5bf64; RAIL_EXPIRATION=1576079066345; RAIL_DEVICEID=UVYyi6TIH6jYw3jP84uY1oFBa25H3GyNu2H_zDPdGNtxDfUwvi9xnp2lhbphPbN70W4xPC4dBq1aJkmy34jpuJM68f9slZOVzu50NXX1yjnDSkv_Lj3AEW6egqqMZOSb0zXYwMsUSwqMZgY2cJT9Vyyt-nl7216N; _jc_save_fromStation=%u676D%u5DDE%2CHZH; _jc_save_toStation=%u90D1%u5DDE%2CZZF; _jc_save_toDate=2019-12-08; _jc_save_wfdc_flag=dc; BIGipServerpassport=954728714.50215.0000; BIGipServerportal=3134456074.17183.0000; BIGipServerotn=250610186.38945.0000; _jc_save_fromDate=2019-12-21";
    static String repatToken="";
    static JSONObject jsonObject;

    @Override
    public BaseResponse<List<String>> getList() {
        BaseResponse<List<String>> baseResponse=new BaseResponse<>();
        String uri="https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date=2019-12-13&leftTicketDTO.from_station=HZH&leftTicketDTO.to_station=AOH&purpose_codes=ADULT";

        String s= HttpUtil.get(uri);
        if(s!=null){
            JSONObject jsonObject = JSONObject.parseObject(s);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray jsonArray = data.getJSONArray("result");
            List<String> list = jsonArray.toJavaList(String.class);
            baseResponse.setData(list);
            String valueStr = list.stream().filter(o -> {
                String[] arr = o.split("\\|");
                if (arr[3].equals("G7504")) {
                    return true;
                }
                return false;
            }).findFirst().get();
            submitOrder(valueStr);
            getDoc();
            getUser();
            checkOrderInfo(null);
            getQueueCount();
            return baseResponse;
        }
       return null;
    }

    void submitOrder(String valueStr){
        try {
            String[] values = valueStr.split("\\|");
            String url="https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest";
            Map<String,String> map=new HashMap<>();
            String decode = URLDecoder.decode(values[0], "UTF-8");
            map.put("secretStr",decode);
            map.put("train_date","2019-12-13");
            map.put("back_train_date","2019-12-08");
            map.put("tour_flag","dc");
            map.put("purpose_codes","ADULT");
            map.put("query_from_station_name","杭州");
            map.put("query_to_station_name","上海");
            map.put("undefined",null);
            String post = HttpUtil.post(url, map);
            System.out.println("提交订单结果+"+post);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void getDoc(){
        String url="https://kyfw.12306.cn/otn/confirmPassenger/initDc";
        try {
            Map<String,String> map=new HashMap<>();
//            map.put("secretStr",decode);
            String s = HttpUtil.post(url, map);
            Pattern p = Pattern.compile("globalRepeatSubmitToken = '(.*)';");
            Matcher m = p.matcher(s);
            if(m.find()){
                repatToken=m.group(1);
                System.out.println(m.group(1));
            }
            Pattern p2 = Pattern.compile("var ticketInfoForPassengerForm=(.*);");
            Matcher m2 = p2.matcher(s);
            if(m2.find()){
                String group = m2.group(1);
                String replace = group.replace("'", "\"");
                jsonObject = JSONObject.parseObject(replace);
                System.out.println(jsonObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void getUser(){
        String url="https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs";
        Map<String,String> map=new HashMap<>();
        map.put("REPEAT_SUBMIT_TOKEN",repatToken);
        String post = HttpUtil.post(url, map);
        JSONObject jsonObject = JSONObject.parseObject(post);
        Object data = jsonObject.get("data");
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("normal_passengers");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String passenger_name = object.getString("passenger_name");
        }

        System.out.println("获取乘客结果"+jsonArray);
    }

    void checkOrderInfo(JSONObject user){
        String url="https://kyfw.12306.cn/otn/confirmPassenger/checkOrderInfo";
        Map<String,String> map=new HashMap<>();
        map.put("cancel_flag","2");
        map.put("bed_level_order_num","000000000000000000000000000000");
        map.put("passengerTicketStr","O,0,1,杨晓飞,1,4104***********011,13588200025,N,f8f1cce2f52df322ab41bd39052f6849b0a0cf1009f8ed97d61d21b1822636d69fe255753290b9a5feb646b5eb082827");
        map.put("oldPassengerStr","杨晓飞,1,4104***********011,1_");
        map.put("tour_flag","dc");
        map.put("randCode",null);
        map.put("whatsSelect","1");
        map.put("_json_att",null);
        map.put("REPEAT_SUBMIT_TOKEN",repatToken);
        String post = HttpUtil.post(url, map);
        System.out.println("检查订单信息返回信息为"+post);
    }

    void getQueueCount(){
        String url="https://kyfw.12306.cn/otn/confirmPassenger/getQueueCount";
        Map<String,String> map=new HashMap<>();
        map.put("train_date","Fri Dec 13 2019 00:00:00 GMT+0800 (中国标准时间)");
        map.put("train_no","5j000G750403");
        map.put("stationTrainCode","G7504");
        map.put("seatType","O");
        map.put("fromStationTelecode","HGH");
        map.put("toStationTelecode","AOH");
        map.put("leftTicket","gWD%2B2CLkL8HOlrPrnP4spw2plRKuwjyJq9dgcTF5P1FQHoszxM%2FHE69cpIY%3D");
        map.put("purpose_codes","00");
        map.put("train_location","H3");
        map.put("_json_att",null);
        map.put("REPEAT_SUBMIT_TOKEN",repatToken);
        String post = HttpUtil.post(url, map);
        System.out.println("获取排队结果为"+post);
    }

    void confirm(){
        String url="https://kyfw.12306.cn/otn/confirmPassenger/confirmSingleForQueue";
        Map<String,String> map=new HashMap<>();
        map.put("passengerTicketStr","O,0,1,杨晓飞,1,4104***********011,13588200025,N,f8f1cce2f52df322ab41bd39052f6849b0a0cf1009f8ed97d61d21b1822636d69fe255753290b9a5feb646b5eb082827");
        map.put("oldPassengerStr","杨晓飞,1,4104***********011,1_");
        map.put("randCode",null);
        map.put("purpose_codes","O0");
        map.put("key_check_isChange","53A0311CB0C59A17CB8254F53EFAB9D6FCCEB76E0CCFC6A46D3D7D24");
        map.put("leftTicketStr","gWD%2B2CLkL8HOlrPrnP4spw2plRKuwjyJq9dgcTF5P1FQHoszxM%2FHE69cpIY%3D");
        map.put("train_location","H3");
        map.put("choose_seats",null);
        map.put("seatDetailType","000");
        map.put("whatsSelect","1");
        map.put("roomType","00");
        map.put("dwAll","N");
        map.put("_json_att",null);
        map.put("REPEAT_SUBMIT_TOKEN",repatToken);
        String post = HttpUtil.post(url, map);
        System.out.println("获取排队结果为"+post);
    }

    @Override
    public BaseResponse<String> getLoginImg() {
        String uri="https://kyfw.12306.cn/passport/captcha/captcha-image64?login_site=E&module=login&rand=sjrand&1575794316003&callback=jQuery1910532979658339602_1575794312013&_=1575794312014";
        return null;
    }
}
