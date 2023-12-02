package com.aking.androidstartup.manager;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rick at 2023-11-06 21:02.
 * Description:
 */
public class ExecutorManager {
    /**
     * CPU密集型，如果代码执行比较多
     */
    public static ThreadPoolExecutor sCpuExecutor;
    /**
     * IO密集型
     */
    public static ExecutorService sIoExecutor;

    /**
     * 主线程
     */
    public static Executor sMainExecutor;

    //获得当前CPU的核心数
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //设置线程池的核心线程数2-5之间,但是取决于CPU核数
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 5));
    //设置线程池的最大线程数为 CPU核数
    private static final int MAX_POOL_SIZE = CORE_POOL_SIZE;
    //设置线程池空闲线程存活时间5s
    private static final long KEEP_ALIVE_TIME = 5L;

    static {
        sCpuExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>());
        sCpuExecutor.allowCoreThreadTimeOut(true);

        sIoExecutor = Executors.newCachedThreadPool(Executors.defaultThreadFactory());

        sMainExecutor = new Executor() {
            final Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }

}
