package com.aking.androidstartup.run;

import android.content.Context;
import android.os.Process;

import com.aking.androidstartup.manager.StartupCacheManager;
import com.aking.androidstartup.manager.StartupManager;
import com.aking.androidstartup.startup.Result;
import com.aking.androidstartup.startup.Startup;

/**
 * Created by Rick at 2023-11-06 23:10.
 * Description:
 */
public class StartupRunnable implements Runnable {
    private final StartupManager mStartupManager;
    private final Startup<?> mStartup;
    private final Context mContext;

    public StartupRunnable(Context context, Startup<?> startup, StartupManager startupManager) {
        mStartupManager = startupManager;
        mStartup = startup;
        mContext = context;
    }

    @Override
    public void run() {
        Process.setThreadPriority(mStartup.getThreadPriority());
        mStartup.toWait();
        Object result = mStartup.create(mContext);
        StartupCacheManager.getInstance().saveInitializedComponent(mStartup.getClass(), new Result<>(result));
        //当前任务执行完成后，调用后序任务的toNotify()
        mStartupManager.notifyChildren(mStartup);
    }
}
