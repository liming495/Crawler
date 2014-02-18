package com.ming.dao;

import java.util.Set;

/**
 * 持久化.
 * User: Ming Li
 * Time: 13-10-11 下午5:53
 */
public interface ICrawlerDAO {

    /**
     * 网站数据入库
     * @param webPage   网站
     */
    public void addWebPage(String webPage);

    /**
     * 获取全部网站数据
     * @return  网站数据
     */
    public Set<String> getAllWebPage();

    /**
     * 随机获取网站数据
     * @return 网站数据
     */
    public String getRandomWebPage();

    /**
     * 搜索网站
     * @param key   关键字
     * @param findType  搜索类型
     * @return  网站
     */
    public String findSiteByKey(String key, String findType);

    /**
     * 未爬取的网址入库
     * @param webPageCur    未爬取的网址
     */
    public void addWebPageCur(String webPageCur);

    /**
     * 获取未爬取的网址
     * @return  未爬取的网址
     */
    public Set<String> getWebPageCur();

    /**
     * 删除未爬取的网址
     * @param webPageCur    未爬取的网址
     */
    public void delWebPageCur(String webPageCur);

    /**
     * 未爬取的网址出队
     * @return  未爬的网址
     */
    public String pushWebPageCur();
}
