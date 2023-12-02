package com.aking.androidstartup.manager;

import com.aking.androidstartup.startup.Result;
import com.aking.androidstartup.startup.Startup;

import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Rick at 2023-11-06 10:06.
 * Description: 缓存执行完成的任务结果
 */
public class StartupCacheManager {

    /**
     * 用于缓存任务的执行结果
     */
    private final ConcurrentHashMap<Class<? extends Startup>, Result> mComponentsResult = new ConcurrentHashMap<>();

    private StartupCacheManager() {
    }

    private static final class SInstanceHolder {
        static final StartupCacheManager sInstance = new StartupCacheManager();
    }

    public static StartupCacheManager getInstance() {
        return SInstanceHolder.sInstance;
    }

    /**
     * save result of initialized component.
     */
    public void saveInitializedComponent(Class<? extends Startup> zClass, Result result) {
        mComponentsResult.put(zClass, result);
    }

    /**
     * check initialized.
     */
    public boolean hadInitialized(Class<? extends Startup> zClass) {
        return mComponentsResult.containsKey(zClass);
    }

    public <T> Result<T> obtainInitializedResult(Class<? extends Startup<T>> zClass) {
        return mComponentsResult.get(zClass);
    }


    public void remove(Class<? extends Startup> zClass) {
        mComponentsResult.remove(zClass);
    }

    public void clear() {
        mComponentsResult.clear();
    }
}
