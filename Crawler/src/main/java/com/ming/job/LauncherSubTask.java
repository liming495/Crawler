package com.ming.job;

import com.ming.vo.Site;

import java.io.Serializable;

/**
 * 没时间写说明拉,帮忙写下 ^-^.
 * User: Ming Li
 * Time: 14-2-7 上午10:12
 */
public class LauncherSubTask implements Runnable, Serializable {

    private Site site;
    private LauncherJob launcherJob;

    public LauncherSubTask(LauncherJob launcherJob, Site site) {
        this.site = site;
        this.launcherJob = launcherJob;
    }

    @Override
    public void run() {
        launcherJob.execute(site);
    }

}
