package com.fei.ticket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {


    public static void main(String[] args) throws UnsupportedEncodingException {
        String cookie="JSESSIONID=FBA9964CC9450C71B73D06838AA36868; tk=6bJ8DxHAsOutIL6DqmpkvNEmJ-IB36jE4xapvg73j2j0; BIGipServerpool_passport=283968010.50215.0000; route=c5c62a339e7744272a54643b3be5bf64; RAIL_EXPIRATION=1576079066345; RAIL_DEVICEID=UVYyi6TIH6jYw3jP84uY1oFBa25H3GyNu2H_zDPdGNtxDfUwvi9xnp2lhbphPbN70W4xPC4dBq1aJkmy34jpuJM68f9slZOVzu50NXX1yjnDSkv_Lj3AEW6egqqMZOSb0zXYwMsUSwqMZgY2cJT9Vyyt-nl7216N; _jc_save_toDate=2019-12-08; _jc_save_wfdc_flag=dc; BIGipServerpassport=954728714.50215.0000; BIGipServerportal=3134456074.17183.0000; BIGipServerotn=250610186.38945.0000; _jc_save_showIns=true; _jc_save_fromDate=2019-12-13; _jc_save_fromStation=%u676D%u5DDE%2CHGH; _jc_save_toStation=%u4E0A%u6D77%2CAOH";

        String uri="https://kyfw.12306.cn/otn/confirmPassenger/checkOrderInfo";
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();



        // 创建Get请求
        HttpPost httpPost = new HttpPost(uri);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        NameValuePair pair1 = new BasicNameValuePair("cancel_flag","2");
        NameValuePair pair2 = new BasicNameValuePair("bed_level_order_num","000000000000000000000000000000");
        NameValuePair pair3 = new BasicNameValuePair("passengerTicketStr","3,0,1,杨晓飞,1,4104***********011,13588200025,N,f8f1cce2f52df322ab41bd39052f6849b0a0cf1009f8ed97d61d21b1822636d69fe255753290b9a5feb646b5eb082827");
        NameValuePair pair4 = new BasicNameValuePair("oldPassengerStr","杨晓飞,1,4104***********011,1_");
        NameValuePair pair5 = new BasicNameValuePair("tour_flag","dc");
        NameValuePair pair6 = new BasicNameValuePair("randCode",null);
        NameValuePair pair7 = new BasicNameValuePair("whatsSelect","1");
        NameValuePair pair8 = new BasicNameValuePair("_json_att",null);
        NameValuePair pair9 = new BasicNameValuePair("REPEAT_SUBMIT_TOKEN","583d0693991ba3af1e3dee7fb40533f8");

        pairs.add(pair1);
        pairs.add(pair2);
        pairs.add(pair3);
        pairs.add(pair4);
        pairs.add(pair5);
        pairs.add(pair6);
        pairs.add(pair7);
        pairs.add(pair8);
        pairs.add(pair9);


        // 将user对象转换为json字符串，并放入entity中
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, HTTP.UTF_8);

        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.setHeader("Cookie",cookie);
        httpPost.setEntity(entity);

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {

                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
