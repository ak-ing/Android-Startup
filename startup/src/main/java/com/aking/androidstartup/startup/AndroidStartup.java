package com.aking.androidstartup.startup;

import android.content.Context;
import android.os.Process;

import com.aking.androidstartup.manager.ExecutorManager;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * Created by Rick at 2023-11-04 21:09.
 * Description:
 */
public abstract class AndroidStartup<T> implements Startup<T> {

    /**
     * 计数器，初始化为当前任务的入度数{@link AndroidStartup#getDependenciesCount()}。
     * 执行{@link AndroidStartup#toWait()}会使当前任务进入等待状态，直到计数器为0
     */
    private final CountDownLatch mWaitCountDown = new CountDownLatch(getDependenciesCount());

    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return null;
    }

    @Override
    public int getDependenciesCount() {
        List<Class<? extends Startup<?>>> dependencies = dependencies();
        return dependencies == null ? 0 : dependencies.size();
    }

    /**
     * 当前任务进入等待状态，直到{@link AndroidStartup#mWaitCountDown}为0
     */
    @Override
    public void toWait() {
        try {
            mWaitCountDown.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 有父任务(依赖)执行完毕
     * 通知当前任务{@link AndroidStartup#mWaitCountDown} -1
     */
    @Override
    public void toNotify() {
        mWaitCountDown.countDown();
    }

    /**
     * 指定当前任务执行在哪个线程池{@link  ExecutorManager}
     *
     * @return 默认使用io线程池 {@link  ExecutorManager#sIoExecutor}
     */
    @Override
    public Executor executor() {
        return ExecutorManager.sIoExecutor;
    }

    /**
     * 指定线程优先级
     *
     * @return 任务执行的线程优先级，默认最高优先级
     */
    @Override
    public int getThreadPriority() {
        return Process.THREAD_PRIORITY_DEFAULT;
    }

    /**
     * 是否在主线程执行 {@link AndroidStartup#create(Context)}
     *
     * @return 默认值：false，在子线程执行
     */
    @Override
    public boolean callCreateOnMainThread() {
        return false;
    }

    /**
     * 当前任务运行在子线程时，是否需要主线程等待该任务执行完成
     *
     * @return 默认不需要主线程等待自己执行完成
     */
    @Override
    public boolean waitOnMainThread() {
        return false;
    }
}
