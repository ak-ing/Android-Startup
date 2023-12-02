package com.aking.androidstartup.manager;

import android.content.Context;
import android.os.Looper;

import androidx.annotation.MainThread;

import com.aking.androidstartup.run.StartupRunnable;
import com.aking.androidstartup.sort.TopologySort;
import com.aking.androidstartup.startup.Startup;
import com.aking.androidstartup.startup.StartupSortStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Rick at 2023-11-06 11:07.
 * Description:
 */
public class StartupManager {

    private final CountDownLatch mAwaitCountDownLatch;
    private final Context mContext;
    private final List<Startup<?>> mStartupList;
    private StartupSortStore mStartupSortStore;

    public StartupManager(Context context, List<Startup<?>> startupList, CountDownLatch awaitCountDownLatch) {
        mContext = context;
        mStartupList = startupList;
        mAwaitCountDownLatch = awaitCountDownLatch;
    }

    @MainThread
    public StartupManager start() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("请在主线程调用！");
        }
        mStartupSortStore = TopologySort.sort(mStartupList);
        for (Startup<?> startup : mStartupSortStore.getResult()) {
            StartupRunnable startupRunnable = new StartupRunnable(mContext, startup, this);
            if (startup.callCreateOnMainThread()) {
                startupRunnable.run();
            } else {
                startup.executor().execute(startupRunnable);
            }
        }
        return this;
    }

    public void notifyChildren(Startup<?> startup) {
        if (!startup.callCreateOnMainThread() && startup.waitOnMainThread()) {
            mAwaitCountDownLatch.countDown();
        }

        Class<? extends Startup> startupClass = startup.getClass();
        Map<Class<? extends Startup>, List<Class<? extends Startup>>> startupChildrenMap = mStartupSortStore.getStartupChildrenMap();
        //通知当前任务的所有子任务
        if (startupChildrenMap.containsKey(startupClass)) {
            List<Class<? extends Startup>> childStartupCls = startupChildrenMap.get(startupClass);
            for (Class<? extends Startup> childStartupCl : childStartupCls) {
                Startup<?> childStartup = mStartupSortStore.getStartupMap().get(childStartupCl);
                Objects.requireNonNull(childStartup).toNotify();
            }
        }
    }

    public void await() {
        try {
            mAwaitCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean await(long timeout, TimeUnit unit) {
        try {
            return mAwaitCountDownLatch.await(timeout, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 构造一个 StartupManager
     */
    public static class Builder {
        private final List<Startup<?>> mStartupList = new ArrayList<>();

        public Builder addStartup(Startup<?> startup) {
            mStartupList.add(startup);
            return this;
        }

        public Builder addAllStartup(List<Startup<?>> startups) {
            mStartupList.addAll(startups);
            return this;
        }

        public StartupManager build(Context context) {
            //记录在子线程执行，并且需要主线程等待该任务执行完成 的任务数量
            AtomicInteger needAwaitCount = new AtomicInteger();
            for (Startup<?> startup : mStartupList) {
                if (!startup.callCreateOnMainThread() && startup.waitOnMainThread()) {
                    needAwaitCount.incrementAndGet();
                }
            }
            //根据任务数新建一个闭锁
            CountDownLatch countDownLatch = new CountDownLatch(needAwaitCount.get());
            return new StartupManager(context, mStartupList, countDownLatch);
        }
    }
}
