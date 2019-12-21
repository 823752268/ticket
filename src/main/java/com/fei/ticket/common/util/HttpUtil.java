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

    private static String cookie = "_uab_collina=157681250956629334406131; JSESSIONID=547C20A6B91E27A950711AF85C6094C1; tk=xyZ4mGWG2lulXURwtayr5HxByZDi8Ijk0HQRFwsdZ1Z0; _jc_save_wfdc_flag=dc; RAIL_DEVICEID=gzsvkHgKnT1Hoez3U044-5WknKMDTFiq3XkEPV8vrdIvAXA4Xh_HehOBskP75bgK3G7XiUoCGcdSfkIMaZO8XF7mJ5t2N8xQljYie7ixswV5sQLDTMC_9iOJp2e0TI2FNukfNXLjYlAcwMSZW7GUja3QGwdGFnd2; RAIL_EXPIRATION=1577030090174; _jc_save_fromStation=%u676D%u5DDE%2CHZH; BIGipServerotn=1240465930.64545.0000; BIGipServerpool_passport=166527498.50215.0000; route=c5c62a339e7744272a54643b3be5bf64; _jc_save_toStation=%u6B66%u6C49%2CWHN; _jc_save_toDate=2019-12-21; _jc_save_fromDate=2020-01-18";


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
