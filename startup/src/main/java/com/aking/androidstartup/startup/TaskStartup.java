package com.aking.androidstartup.startup;

import android.content.Context;

/**
 * Created by Rick at 2023-11-08 10:04.
 * Description: 启动任务的便捷实现，方便在构造方法中传入代理接口的lambda实现
 */
public final class TaskStartup<T> extends AndroidStartup<T> {
    private final TaskStartupDelegate<T> mDelegate;

    public TaskStartup(TaskStartupDelegate<T> delegate) {
        mDelegate = delegate;
    }

    @Override
    public T create(Context context) {
        return mDelegate.create(context);
    }
}
