package com.ming;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;

/**
 * 没时间写说明拉,帮忙写下 ^-^.
 * User: Ming Li
 * Time: 14-2-10 下午3:05
 */
public class HttpClientFactory {
    private static ThreadSafeClientConnManager threadSafeClientConnManager = null;
    private static HttpClient httpClient = null;

    static {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
        threadSafeClientConnManager = new ThreadSafeClientConnManager(schemeRegistry);
        threadSafeClientConnManager.setMaxTotal(200);
        threadSafeClientConnManager.setDefaultMaxPerRoute(50);                              //每条通道的并发连接数设置（连接池）
        HttpParams params = new BasicHttpParams();
        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);                 //3000ms
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);                         //1000ms
        httpClient = new DefaultHttpClient(threadSafeClientConnManager, params);            //实例化一个httpClient
    }

    /**
     * 获取httpClient实例
     * @return httpClient
     */
    public static HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * 关闭整个连接池
     */
    public static void shutdown() {
        if (null != threadSafeClientConnManager) {
            threadSafeClientConnManager.shutdown();
        }
    }
}
