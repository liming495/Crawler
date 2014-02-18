package com.ming.pool;

import com.ming.dao.impl.CrawlerDAO;
import com.ming.job.LauncherJob;
import com.ming.job.LauncherSubTask;
import com.ming.util.Tool;
import com.ming.vo.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 没时间写说明拉,帮忙写下 ^-^.
 * User: Ming Li
 * Time: 14-2-7 上午9:57
 */
public class LauncherPool {
    private static final Logger logger = LoggerFactory.getLogger(LauncherPool.class);
    private int produceTaskSleepTime = 20;          //线程间隔时间单位豪秒
    private int LimitCount = 20;                    //限制线程数
    private int MaxCount = 900;                     //最大线程数
    private int CurrCount = LimitCount;             //当前线程数

    private LauncherJob launcherJob;
    private ThreadPoolExecutor threadPoolExecutor;

    private CrawlerDAO crawlerDAO;

    public void launcher(){
        //ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        Tool.LocalWebPageIdle = new ArrayList<String>(Tool.WebPageIdle);
        if (Tool.WebPageCurQueue.size() < CurrCount) {
            CurrCount += 10;
        } else {
            CurrCount -= 10;
        }

        CurrCount = CurrCount < LimitCount ? LimitCount : CurrCount;
        CurrCount = CurrCount > MaxCount ? MaxCount : CurrCount;

        while (threadPoolExecutor.getActiveCount() < CurrCount) {
            Site site = getSiteUrl();
            threadPoolExecutor.execute(new LauncherSubTask(launcherJob, site));
            //等待一段时间
            try {
                Thread.sleep(produceTaskSleepTime);
            } catch (InterruptedException e) {
                logger.error("等待异常!");
            }
        }
    }

    public void count(){
        logger.warn("[活动线程总数:{}][线程池大小:{}][队列长度:" + threadPoolExecutor.getQueue().size() + "][内部队列长度" + Tool.WebPageCurQueue.size() + "]" + "[核心池" + Tool.WebPageIdle.size() + "]", threadPoolExecutor.getActiveCount(), threadPoolExecutor.getPoolSize());
    }

    public Site getSiteUrl() {
        boolean flag = false;
        String requestUrl;
        if (Tool.WebPageCurQueue.isEmpty()) {
                requestUrl = crawlerDAO.pushWebPageCur();
                if (null == requestUrl) {
                    if (Tool.LocalWebPageIdle.isEmpty()) {
                        requestUrl = crawlerDAO.getRandomWebPage();
                    } else {
                        List<String> list = Tool.LocalWebPageIdle;
                        requestUrl = list.get((int)(Math.random()*list.size()));
                    }
                    logger.warn("requestUrl is [{}]", requestUrl);
                    flag = true;
            }
        } else {
                requestUrl = Tool.WebPageCurQueue.poll();
                Tool.WebPageCur.remove(requestUrl);
                if (Tool.WebPageCurQueue.size() < 2) {
                    logger.debug("[WebPageCurQueue info is {}][{}]", Tool.WebPageCurQueue.size(), requestUrl);
                }
        }
        return new Site(0, flag, requestUrl);
    }

    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    public void setLauncherJob(LauncherJob launcherJob) {
        this.launcherJob = launcherJob;
    }

    public void setProduceTaskSleepTime(int produceTaskSleepTime) {
        this.produceTaskSleepTime = produceTaskSleepTime;
    }

    public void setLimitCount(int limitCount) {
        LimitCount = limitCount;
    }

    public void setCrawlerDAO(CrawlerDAO crawlerDAO) {
        this.crawlerDAO = crawlerDAO;
    }

    public void setMaxCount(int maxCount) {
        MaxCount = maxCount;
    }
}
