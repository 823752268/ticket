package com.fei.ticket.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HttpUtil {

    private static String cookie = "JSESSIONID=9446C189CE229448E0B4724042D542CE; tk=0z3wPC1Fa-uQFXz_rWqLAnwHX3BN6jmCVWOCAwsdj2j0; RAIL_EXPIRATION=1576160003967; RAIL_DEVICEID=VpViUQpCsHi3KlZ7WMprFTh8BaTfXodgFIjGdPoYR1ATO-ZmrCQh--uZx1-jHkLASHVoB26n4NxAxE8F5n5ZiYv3SDgcNObbYsN-me1Ro1Jk-0Ja6kNmAkkneCWQ6gj68Wiq1iQckOV29MZPWSED5fDAAlLU6RQz; BIGipServerpool_passport=300745226.50215.0000; _jc_save_showIns=true; route=495c805987d0f5c8c84b14f60212447d; BIGipServerotn=619708938.24610.0000; _jc_save_fromDate=2019-12-13; _jc_save_wfdc_flag=dc; _jc_save_toDate=2019-12-13; _jc_save_fromStation=%u676D%u5DDE%2CHGH; _jc_save_toStation=%u4E0A%u6D77%2CAOH";




    public static String post(String url, Map<String, String> params) {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        SSLContext sslContext = null;
//        try {
//            sslContext = SSLContextBuilder.create().useProtocol(SSLConnectionSocketFactory.SSL).loadTrustMaterial((x, y) -> true).build();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        }
//        RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
//        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).setSSLContext(sslContext).setSSLHostnameVerifier((x, y) -> true).build();
//        HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");

        HttpPost httpPost = new HttpPost(url);
//        RequestConfig config2 = RequestConfig.custom().setProxy(proxy).build();
//        httpPost.setConfig(config2);
//        httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
//        httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
//        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
//        httpPost.setHeader("Cache-Control", "no-cache");
//        httpPost.setHeader("Connection", "keep-alive");

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.setHeader("Cookie", cookie);

//        httpPost.setHeader("Host", "kyfw.12306.cn");
//        httpPost.setHeader("Origin", "https://kyfw.12306.cn");
//        httpPost.setHeader("Pragma", "no-cache");
//        httpPost.setHeader("Referer", "https://kyfw.12306.cn/otn/confirmPassenger/initDc");
//        httpPost.setHeader("Sec-Fetch-Mode", "cors");
//        httpPost.setHeader("Sec-Fetch-Site", "same-origin");
//        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
//        httpPost.setHeader("X-Requested-With", "XMLHttpRequest");




        List<NameValuePair> pairs = new ArrayList<>();

        JSONObject postData = new JSONObject();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            NameValuePair pair = new BasicNameValuePair(key, params.get(key));
            pairs.add(pair);

            postData.put(key, params.get(key));

        }

        if (params.size() > 0) {
            try {
                String s1 = postData.toString();
//                httpPost.setEntity(new StringEntity(s1, HTTP.UTF_8));
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, HTTP.UTF_8);
                String s = entity.toString();
                httpPost.setEntity(entity);
            } catch (Exception e) {

            }
        }
        // 响应模型
        CloseableHttpResponse response = null;
        try {
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
        httpGet.setHeader("Cookie", cookie);
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
