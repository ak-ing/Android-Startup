package com.aking.androidstartup.startup;

import android.content.Context;

import java.util.concurrent.Executor;

/**
 * Created by Rick at 2023-11-06 21:53.
 * Description:
 */
interface Dispatcher {

    /**
     * 是否在主线程执行 {@link AndroidStartup#create(Context)}
     */
    boolean callCreateOnMainThread();

    /**
     * 指定当前任务执行在哪个线程池
     *
     * @return 任务执行的线程池
     */
    Executor executor();

    /**
     * 指定线程优先级
     */
    int getThreadPriority();

    /**
     * 当前任务进入等待状态
     */
    void toWait();

    /**
     * 通知唤醒
     */
    void toNotify();

    /**
     * 是否需要主线程等待该任务执行完成
     */
    boolean waitOnMainThread();
}
