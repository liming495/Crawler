package com.ming.service;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Crawler WebService.
 * User: Ming Li
 * Time: 13-10-14 上午9:31
 */
@WebService(targetNamespace = "http://crawler.ming.com", name = "CrawlerService")
public interface ICrawler {

    public String findSite(
            @WebParam(targetNamespace = "http://crawler.ming.com", name = "key")
            String key
    );
}
