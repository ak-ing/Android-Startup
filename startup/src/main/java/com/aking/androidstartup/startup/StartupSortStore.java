package com.aking.androidstartup.startup;

import java.util.List;
import java.util.Map;

/**
 * Created by Rick at 2023-11-04 21:29.
 * Description:
 */
public class StartupSortStore {

    /**
     * 排序后的所有任务
     */
    List<Startup<?>> mResult;

    /**
     * 任务表原图
     */
    Map<Class<? extends Startup>, Startup<?>> mStartupMap;

    /**
     * 任务依赖表
     */
    Map<Class<? extends Startup>, List<Class<? extends Startup>>> mStartupChildrenMap;

    public StartupSortStore(List<Startup<?>> result, Map<Class<? extends Startup>, Startup<?>> startupMap, Map<Class<? extends Startup>, List<Class<? extends Startup>>> startupChildrenMap) {
        mResult = result;
        mStartupMap = startupMap;
        mStartupChildrenMap = startupChildrenMap;
    }

    public List<Startup<?>> getResult() {
        return mResult;
    }

    public void setResult(List<Startup<?>> result) {
        mResult = result;
    }

    public Map<Class<? extends Startup>, Startup<?>> getStartupMap() {
        return mStartupMap;
    }

    public void setStartupMap(Map<Class<? extends Startup>, Startup<?>> startupMap) {
        mStartupMap = startupMap;
    }

    public Map<Class<? extends Startup>, List<Class<? extends Startup>>> getStartupChildrenMap() {
        return mStartupChildrenMap;
    }

    public void setStartupChildrenMap(Map<Class<? extends Startup>, List<Class<? extends Startup>>> startupChildrenMap) {
        mStartupChildrenMap = startupChildrenMap;
    }
}
