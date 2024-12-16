package http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2023/9/1
 * @description: TODO
 */
public class HttpUtils {
    private static CloseableHttpClient httpClient = null;

    static {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        // 总连接池数量
        connectionManager.setMaxTotal(2);
        // 可为每个域名设置单独的连接池数量
        connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost("127.0.0.1")), 1);
        // setConnectTimeout：设置建立连接的超时时间
        // setConnectionRequestTimeout：从连接池中拿连接的等待超时时间
        // setSocketTimeout：发出请求后等待对端应答的超时时间
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setConnectTimeout(1000)
//                .setConnectionRequestTimeout(2000)
//                .setSocketTimeout(3000)
//                .build();
        // 重试处理器，StandardHttpRequestRetryHandler
        HttpRequestRetryHandler retryHandler = new StandardHttpRequestRetryHandler();

        httpClient = HttpClients.custom().setConnectionManager(connectionManager)
//                .setDefaultRequestConfig(requestConfig)
//                .setRetryHandler(retryHandler)
                .build();
    }

    public static String doHttpGet(String uri, Map<String, String> getParams) {
        CloseableHttpResponse response = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            if (null != getParams && !getParams.isEmpty()) {
                List<NameValuePair> list = new ArrayList<>();
                for (Map.Entry<String, String> param : getParams.entrySet()) {
                    list.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
                uriBuilder.setParameters(list);
            }
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    String resStr = EntityUtils.toString(entity, "utf-8");
                    return resStr;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("CloseableHttpClient-get-请求异常");
        } finally {
            try {
                if (null != response)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String doHttpPost(String uri, Map<String, String> getParams) {
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(uri);
            if (null != getParams && !getParams.isEmpty()) {
                List<NameValuePair> list = new ArrayList<>();
                for (Map.Entry<String, String> param : getParams.entrySet()) {
                    list.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
                HttpEntity httpEntity = new UrlEncodedFormEntity(list, "utf-8");
                httpPost.setEntity(httpEntity);
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    String resStr = EntityUtils.toString(entity, "utf-8");
                    return resStr;
                }
            }
        } catch (Exception e) {
            System.out.println("CloseableHttpClient-post-请求异常");
        } finally {
            try {
                if (null != response)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return " ";
    }

}
