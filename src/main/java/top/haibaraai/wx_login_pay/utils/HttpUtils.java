package top.haibaraai.wx_login_pay.utils;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装http get post
 */
public class HttpUtils {

    private static final Gson gson = new Gson();

    /**
     * 封装get
     * @param url
     * @return
     */
    public static Map<String, Object> doGet(String url) {

        Map<String, Object> map = new HashMap<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000) //连接超时时间
                .setConnectionRequestTimeout(5000) //请求获取数据的超时时间
                .setRedirectsEnabled(true)
                .build();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);

        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                String jsonResult = EntityUtils.toString(entity);
                map = gson.fromJson(jsonResult, map.getClass());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 封装post
     * @param url
     * @param data
     * @return
     */
    public static String doPost(String url, String data) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000) //连接超时时间
                .setConnectionRequestTimeout(5000) //请求获取数据的超时时间
                .setRedirectsEnabled(true)
                .build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "text/html; charset=UTF-8");
        httpPost.setConfig(requestConfig);

        if (data != null) {
            StringEntity entity = new StringEntity(data, "UTF-8");
            httpPost.setEntity(entity);
        }

        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpPost.getEntity();
                String result = EntityUtils.toString(httpEntity);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;

    }

}
