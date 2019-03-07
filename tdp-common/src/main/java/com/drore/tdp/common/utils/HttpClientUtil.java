package com.drore.tdp.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/14  14:12.
 */
@Slf4j
public class HttpClientUtil {

    private static RequestConfig requestConfig = null;

    static {
        // 设置请求和传输超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
    }

    /**
     * 发送get请求
     *
     * @param url 路径
     * @return
     */
    public static JSONObject httpGet(String url) {
        // get请求返回结果
        JSONObject jsonResult = null;
        CloseableHttpClient client = HttpClients.createDefault();
        // 发送get请求
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = client.execute(request);
            // 请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 读取服务器返回过来的json字符串数据
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity, "utf-8");
                // 把json字符串转换成json对象
                jsonResult = JSONObject.parseObject(strResult);
            } else {
                log.error("[get请求提交失败url] {}", url);
            }
        } catch (IOException e) {
            log.error("[get请求提交异常url] {}\n[error] {}", url, e);
        } finally {
            request.releaseConnection();
        }
        return jsonResult;
    }

    /**
     * post请求传输json参数
     *
     * @param url       url地址
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        jsonResult = getJsonObject(url, jsonParam, httpClient, jsonResult, httpPost);
        return jsonResult;
    }

    /**
     * post请求传输json参数
     *
     * @param url       url地址
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam, Integer socketTimeout, Integer connectTimeout) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        RequestConfig config = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        httpPost.setConfig(config);
        jsonResult = getJsonObject(url, jsonParam, httpClient, jsonResult, httpPost);
        return jsonResult;
    }

    public static <T> T httpPost(String url, JSONObject jsonParam, Class<T> t) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        jsonResult = getJsonObject(url, jsonParam, httpClient, jsonResult, httpPost);
        if (Objects.isNull(jsonResult)) {
            return JSONObject.parseObject(JSON.toJSONString(jsonResult), t);
        } else {
            return null;
        }
    }

    private static JSONObject getJsonObject(String url, JSONObject jsonParam, CloseableHttpClient httpClient, JSONObject jsonResult, HttpPost httpPost) {
        try {
            if (null != jsonParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 读取服务器返回过来的json字符串数据
                String str = EntityUtils.toString(result.getEntity(), "utf-8");
                // 把json字符串转换成json对象
                jsonResult = JSONObject.parseObject(str);
                log.debug("[post请求提交成功] {}\n[请求参数] {}", url, jsonParam);
            } else {
                log.error("[post请求提交失败] {}\n[请求参数] {}", url, jsonParam);
            }
        } catch (IOException e) {
            log.error("[post请求提交异常] {}\n[请求参数] {}\n[error] {}", url, jsonParam, e);
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    public static void main(String[] args) {
        while (true) {
            getSource("http://ms.mayikt.com/detail/1");
        }
    }

    public static String getSource(String url) {
        String html = new String();
        //创建Http请求实例，URL 如：https://cd.lianjia.com/
        HttpGet httpget = new HttpGet(url);
        // 模拟浏览器，避免被服务器拒绝，返回返回403 forbidden的错误信息
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
        CloseableHttpResponse response = null;
        // 使用默认的HttpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            response = httpclient.execute(httpget);
            // 返回 200 表示成功
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 获取服务器响应实体的内容
                html = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return html;
    }
}
