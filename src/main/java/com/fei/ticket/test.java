package com.fei.ticket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fei.ticket.common.util.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {


    public static void main(String[] args) throws UnsupportedEncodingException {
        String url="https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest";
        Map<String,String> map=new HashMap<>();
        String decode = URLDecoder.decode("yWIdm3fn7tR4PMxVmG8w6h9lW3akOFnpWUmJnFTDAbRogOW7qwzXiAplKb2a0S4lUwm6q5bhoDk4%0AS9Qu7TR2rUDBlODBuvAT0K4QCE3mNfrQiXk3U%2BcYCOOxdG235I7XYkVqb5v1TAWfO%2BChxwAcdKFJ%0AcRXseb3hZWBReqe8yhu6%2BmCbMsofkYivn3kNgQ5x%2F3LU8WZKtWmuP8rMOecDBwKN8eL6Qus9cKsm%0AuFNtbZAM4%2Fiek2vLrWlrsbnYKsOKe8lqAF%2Bo4QRerEUQHSRFYBdPKIivdw7pPpuErNUhUdGeyG6e%0A", "UTF-8");
        map.put("secretStr",decode);
        map.put("train_date","2020-01-08");
        map.put("back_train_date","2019-12-10");
        map.put("tour_flag","dc");
        map.put("purpose_codes","ADULT");
        map.put("query_from_station_name","杭州东");
        map.put("query_to_station_name","郑州东");
//            map.put("undefined",null);
        String post = HttpUtil.post(url, map);
        System.out.println("提交订单结果+"+post);

    }


}
