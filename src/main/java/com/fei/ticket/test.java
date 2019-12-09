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
        String encode = URLEncoder.encode("P65BynjrkMOW0dtlhubvsl6V05%2B3vuTJXW6UFugOtf49laSy8ZPokOMs3xo%3D", "UTF-8");
        String decode = URLDecoder.decode("P65BynjrkMOW0dtlhubvsl6V05%2B3vuTJXW6UFugOtf49laSy8ZPokOMs3xo%3D", "UTF-8");
        System.out.println(decode);
        NameValuePair pair = new BasicNameValuePair("12", decode);
        System.out.println(pair.getValue());
        String url="https://kyfw.12306.cn/otn/confirmPassenger/confirmSingleForQueue";
        Map<String,String> map=new HashMap<>();
        map.put("passengerTicketStr","O,0,1,杨晓飞,1,4104***********011,13588200025,N,f8f1cce2f52df322ab41bd39052f6849b0a0cf1009f8ed97d61d21b1822636d69fe255753290b9a5feb646b5eb082827");
        map.put("oldPassengerStr","杨晓飞,1,4104***********011,1_");
        map.put("randCode","");
        map.put("purpose_codes","00");
        map.put("key_check_isChange","AD1E4FE07FD66FD4FDBC766C050AD2FCDF516711A4C0E45A976F94D1");
        map.put("leftTicketStr","5%2B2ZK8iNouXEugTDGCHX0yIZx7MhXdmASZGFVAnOm6gBlfgMpNCeumr94G8%3D");
        map.put("train_location","H3");
        map.put("choose_seats","");
        map.put("seatDetailType","000");
        map.put("whatsSelect","1");
        map.put("roomType","00");
        map.put("dwAll","N");
        map.put("_json_att","");
        map.put("REPEAT_SUBMIT_TOKEN","b2a3b20f0ca0f475854bb0245657cc2d");
        String post = HttpUtil.post(url, map);
        System.out.println("提交订单结果为"+post);

    }


}
