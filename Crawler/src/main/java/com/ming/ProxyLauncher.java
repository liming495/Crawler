package com.ming;

import com.ming.util.Tool;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代理入口.
 * User: Ming Li
 * Time: 13-10-11 下午4:29
 */
public class ProxyLauncher {
    private static final Logger logger = LoggerFactory.getLogger(ProxyLauncher.class);

    public static String getMessages(String requestUrl){
        HttpClient httpclient = HttpClientFactory.getHttpClient();
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 6000); //设置连接超时
        HttpConnectionParams.setSoTimeout(params, 3000); //设置请求超时
        HttpPost httpPost = new HttpPost(completionUrl(requestUrl));
        httpPost.setParams(params);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null && HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
                InputStream inputStream = entity.getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String str;
                while (null != (str = bufferedReader.readLine())) {
                    stringBuilder.append(str);
                }
            }
        } catch (IOException e) {
            logger.info("[Call URL Error!] --> {}  {}", requestUrl, e.getMessage());
            return Tool.E_10000;
        } finally {
            Tool.WebPageCur.remove(requestUrl);
        }
        return catchWebPage(stringBuilder.toString());
    }

    /**
     * 抓取网站
     * @param context   返回报文
     * @return  网站
     */
    public static String catchWebPage(String context) {
        StringBuilder stringBuilder = new StringBuilder();
        //Pattern p = Pattern.compile("[h][t]{2}[p][:][/][/][w]{3}[\\.][0-9A-Za-z]+[\\.][a-z]{2,3}([\\/][0-9A-Za-z]+)+([\\/][0-9A-Za-z]+[.][a-z]+)?");
        //Pattern p = Pattern.compile("[h][t]{2}[p][s]?[:][/][/][0-9A-Za-z]+[\\.][0-9A-Za-z]+[[\\.][a-zA-Z]]+");//匹配所有网址
        Pattern p = Pattern.compile("[h][t]{2}[p][s]?[:][/][/][w]{3}\\.[0-9a-zA-Z]+\\.[0-9a-zA-Z]+[\\.]*[0-9a-zA-Z]*[\\.a-zA-Z]+");//匹配首页
        Matcher m_phone = p.matcher(context);
        while (m_phone.find()) {
            stringBuilder.append(m_phone.group()).append("|");
        }
        if (stringBuilder.length() > 1) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    public static String completionUrl(String requestUrl){
        Pattern p = Pattern.compile("[h][t]{2}[p][s]?[:][/][/][w]{3}\\.[0-9a-zA-Z]+\\.[0-9a-zA-Z]+[\\.]*[0-9a-zA-Z]*[\\.a-zA-Z]+");//匹配首页
        Matcher m_phone = p.matcher(requestUrl);
        if (m_phone.find()) {
            return requestUrl;
        } else {
            return "http://" + requestUrl;
        }
    }

    public static void main(String args[]){
        String requestUrl = "http://www.design.com.cn.cn.cn";
        //getMessages(requestUrl);
        System.out.println(catchWebPage(requestUrl));
    }

}
