package springcloud.outh2.project.web.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import springcloud.outh2.project.web.entity.JWT;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sir_小三
 * @date 2020/2/5--21:19
 */
public class HttpClientUtils {
    private static final String APPID = "wxe75cf07da058c648";
    private static final String APPSECRET = "d87ee539bcf7b5780935a0b002e1900a";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String CLIENT_GRANT_TYPE = "client_credential";
    private static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    public static JWT httpPost(String username, String password) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://localhost:8762/oauth/token");
        List<BasicNameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("client_id", "clientId");
        BasicNameValuePair param2 = new BasicNameValuePair("client_secret", "123456");
        BasicNameValuePair param3 = new BasicNameValuePair("grant_type", "password");
        BasicNameValuePair param4 = new BasicNameValuePair("username", username);
        BasicNameValuePair param5 = new BasicNameValuePair("password", password);
        list.add(param1);
        list.add(param2);
        list.add(param3);
        list.add(param4);
        list.add(param5);
        UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
        httpPost.setEntity(entityParam);
        // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        // 响应模型
        CloseableHttpResponse response = null;
        // 由客户端执行(发送)Post请求
        response = httpClient.execute(httpPost);
        // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();
        String s = EntityUtils.toString(responseEntity);
        JWT jwt = JSONArray.parseObject(s, JWT.class);
        if (httpClient != null) {
            httpClient.close();
        }
        return jwt;
    }

    public static String JsCode2session(String code) throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/sns/jscode2session");
        List<BasicNameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("appid", APPID);
        BasicNameValuePair param2 = new BasicNameValuePair("secret", APPSECRET);
        BasicNameValuePair param3 = new BasicNameValuePair("js_code", code);
        BasicNameValuePair param4 = new BasicNameValuePair("grant_type", GRANT_TYPE);
        list.add(param1);
        list.add(param2);
        list.add(param3);
        list.add(param4);
        UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
        httpPost.setEntity(entityParam);
        // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        // 响应模型
        CloseableHttpResponse response = null;
        // 由客户端执行(发送)Post请求
        response = httpClient.execute(httpPost);
        // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();
        String s = EntityUtils.toString(responseEntity);
        if (httpClient != null) {
            httpClient.close();
        }
        return s;
    }

    public static void getQrCode(Integer entryFlg, Integer id) throws Exception {
        String accessToken = getAccessToken();
        JSONObject jsonObject = JSONObject.parseObject(accessToken);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + jsonObject.get("access_token"));
        JSONObject paramJson = new JSONObject();
        paramJson.put("scene", "entryFlg=" + entryFlg + "&" + "id=" + id);
        paramJson.put("page", "pages/index/index");
        String s1 = paramJson.toString();
        StringEntity stringEntity = new StringEntity(s1);
        stringEntity.setContentType(CONTENT_TYPE_TEXT_JSON);
        httpPost.setEntity(stringEntity);
        // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
        httpPost.setHeader("Content-Type", "Content-Type:application/json");
        // 响应模型
        CloseableHttpResponse response = null;
        // 由客户端执行(发送)Post请求
        response = httpClient.execute(httpPost);
        // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();
        String s = EntityUtils.toString(responseEntity);
        if (httpClient != null) {
            httpClient.close();
        }

    }


    public static String getAccessToken() throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(GET_ACCESS_TOKEN_URL);
        List<BasicNameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("appid", APPID);
        BasicNameValuePair param2 = new BasicNameValuePair("secret", APPSECRET);
        BasicNameValuePair param4 = new BasicNameValuePair("grant_type", CLIENT_GRANT_TYPE);
        list.add(param1);
        list.add(param2);
        list.add(param4);
        UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
        httpPost.setEntity(entityParam);
        // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        // 响应模型
        CloseableHttpResponse response = null;
        // 由客户端执行(发送)Post请求
        response = httpClient.execute(httpPost);
        // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();
        String s = EntityUtils.toString(responseEntity);
        if (httpClient != null) {
            httpClient.close();
        }
        return s;
    }
}
