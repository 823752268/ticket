package com.fei.ticket.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HttpUtil {

    private static String cookie = "JSESSIONID=9B6D6B1F3B9AB26D64DA1068D7974B2D; tk=72G57m08JGUdQ5vDtW7xROvXl8r5PsbWJICd8g51j1j0; BIGipServerpool_passport=283968010.50215.0000; route=c5c62a339e7744272a54643b3be5bf64; RAIL_EXPIRATION=1576079066345; RAIL_DEVICEID=UVYyi6TIH6jYw3jP84uY1oFBa25H3GyNu2H_zDPdGNtxDfUwvi9xnp2lhbphPbN70W4xPC4dBq1aJkmy34jpuJM68f9slZOVzu50NXX1yjnDSkv_Lj3AEW6egqqMZOSb0zXYwMsUSwqMZgY2cJT9Vyyt-nl7216N; _jc_save_toDate=2019-12-08; _jc_save_wfdc_flag=dc; BIGipServerpassport=954728714.50215.0000; BIGipServerportal=3134456074.17183.0000; _jc_save_showIns=true; _jc_save_fromDate=2019-12-13; _jc_save_fromStation=%u676D%u5DDE%2CHZH; _jc_save_toStation=%u4E0A%u6D77%2CSHH; BIGipServerotn=1339621642.50210.0000";

    public static String post(String url, Map<String, String> params) {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.setHeader("Cookie", cookie);

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            NameValuePair pair = new BasicNameValuePair(key, params.get(key));
            pairs.add(pair);
        }
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, HTTP.UTF_8);
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                return EntityUtils.toString(responseEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null)
                    httpClient.close();
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String get(String url) {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/json;charset=utf8");
        httpGet.setHeader("Cookie",cookie);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                String s = EntityUtils.toString(responseEntity);
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null)
                    httpClient.close();
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
