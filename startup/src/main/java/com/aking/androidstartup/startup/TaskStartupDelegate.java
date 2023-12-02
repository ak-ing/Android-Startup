package com.aking.androidstartup.startup;

import android.content.Context;

/**
 * Created by Rick at 2023-11-08 10:02.
 * Description: 启动任务{@link TaskStartup}的代理接口，作为参数传入，用来增加lambda方式实现
 */
@FunctionalInterface
public
interface TaskStartupDelegate<T> {
    T create(Context context);
}
