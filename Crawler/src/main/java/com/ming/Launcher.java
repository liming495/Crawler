package com.ming;

import com.ming.dao.ICrawlerDAO;
import com.ming.dao.impl.CrawlerDAO;
import com.ming.util.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 服务入口.
 * User: Ming Li
 * Time: 13-10-11 下午3:38
 */
public class Launcher extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);
    private ICrawlerDAO crawlerDAO;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = this.getServletContext();
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        crawlerDAO = ctx.getBean(ICrawlerDAO.class);
        Tool.WebPageIdle = crawlerDAO.getAllWebPage();
        logger.info("爬虫开始启动...");
    }

    public void setCrawlerDAO(CrawlerDAO crawlerDAO) {
        this.crawlerDAO = crawlerDAO;
    }
}
