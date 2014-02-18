package com.ming.job;

import com.ming.ProxyLauncher;
import com.ming.dao.impl.CrawlerDAO;
import com.ming.util.Tool;
import com.ming.vo.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 爬虫启动器.
 * User: Ming Li
 * Time: 13-10-12 上午11:59
 */
public class LauncherJob {
    private static final Logger logger = LoggerFactory.getLogger(LauncherJob.class);
    private CrawlerDAO crawlerDAO;

    private final Object lock = new Object();

    public void execute(Site site) {
        String requestUrl = site.getRequestUrl();
        boolean flag = site.isFlag();

        if (null != site.getRequestUrl() &&requestUrl.isEmpty()) {
            logger.warn("[WebPageCurQueue info is {}]", requestUrl);
            return;
        }

        //if (Tool.WebPageIdle.isEmpty()) {
        //    Tool.WebPageIdle = crawlerDAO.getAllWebPage();
        //}

        if (flag || extContains(requestUrl)) {
            Tool.WebPageIdle.add(requestUrl);
            String contexts = ProxyLauncher.getMessages(requestUrl);
            if (!contexts.isEmpty()) {
                if (!Tool.E_10000.equals(contexts)) {
                    crawlerDAO.addWebPage(requestUrl);
                    String[] context = contexts.split("\\|");
                    for (String page : context) {
                        if (extContains(page) && !Tool.WebPageCur.contains(page)) {
                            //System.out.println(page);
                            synchronized(lock){
                                Tool.WebPageCurQueue.offer(page);
                                Tool.WebPageCur.add(page);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean extContains(String requestUrl){
        boolean isContains = false;
        if (!Tool.WebPageIdle.contains(requestUrl)) {
            String siteUrl = crawlerDAO.findSiteByKey(requestUrl, CrawlerDAO.ACCURATE);
            if (siteUrl.isEmpty()) {
                isContains = true;
            }  else {
                Tool.WebPageIdle.add(requestUrl);
            }
        }
        return isContains;
    }

    public void setCrawlerDAO(CrawlerDAO crawlerDAO) {
        this.crawlerDAO = crawlerDAO;
    }
}
