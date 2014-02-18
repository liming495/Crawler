package com.ming.util;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 公共类.
 * User: Ming Li
 * Time: 13-10-12 下午4:17
 */
public class Tool {
    public static final String E_10000 = "10000";   //调用失败错误码
    public static Set<String> WebPageCur = Collections.synchronizedSet(new HashSet<String>());
    public static Set<String> WebPageIdle = Collections.synchronizedSet(new HashSet<String>());
    public static List<String> LocalWebPageIdle = Collections.synchronizedList(new ArrayList<String>());
    public static Queue<String> WebPageCurQueue = new LinkedBlockingQueue<String>();
    public static Set<String> BlackSet = Collections.synchronizedSet(new HashSet<String>());     //黑名单
}
