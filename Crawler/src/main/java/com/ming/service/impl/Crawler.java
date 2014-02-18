package com.ming.service.impl;

import com.ming.dao.impl.CrawlerDAO;
import com.ming.service.ICrawler;

/**
 * Crawler WebService.
 * User: Ming Li
 * Time: 13-10-14 上午9:28
 */
public class Crawler implements ICrawler {

    private CrawlerDAO crawlerDAO;

    @Override
    public String findSite(String key) {
        String sites = null;
        if (!key.isEmpty()) {
            sites = crawlerDAO.findSiteByKey(key, CrawlerDAO.FUZZY);
        }
        return sites;
    }

    public void setCrawlerDAO(CrawlerDAO crawlerDAO) {
        this.crawlerDAO = crawlerDAO;
    }
}
