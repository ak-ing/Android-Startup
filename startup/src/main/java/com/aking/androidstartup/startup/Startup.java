package com.aking.androidstartup.startup;

import android.content.Context;

import java.util.List;

/**
 * Created by Rick at 2023-11-04 0:15.
 * Description:
 */
public interface Startup<T> extends Dispatcher{

    /**
     * 执行任务逻辑
     */
    T create(Context context);

    /**
     * 当前任务依赖哪些任务
     */
    List<Class<? extends Startup<?>>> dependencies();

    /**
     * 获取当前任务入度数
     *
     * @return 入度数：拓扑排序中的次数
     */
    int getDependenciesCount();
}
