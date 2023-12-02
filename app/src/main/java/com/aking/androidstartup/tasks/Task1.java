package com.aking.androidstartup.tasks;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import com.aking.androidstartup.startup.AndroidStartup;
import com.aking.androidstartup.startup.Startup;

import java.util.List;

public class Task1 extends AndroidStartup<String> {

    @Nullable
    @Override
    public String create(Context context) {
        Log.i("TAG", " Task1：学习Java基础");
        SystemClock.sleep(3_000);
        Log.i("TAG", " Task1：掌握Java基础");
        return "Task1返回的数据";
    }

    //执行此任务需要依赖哪些任务
    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return super.dependencies();
    }

}
