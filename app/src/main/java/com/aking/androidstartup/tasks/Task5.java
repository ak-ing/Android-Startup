package com.aking.androidstartup.tasks;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import com.aking.androidstartup.startup.AndroidStartup;
import com.aking.androidstartup.startup.Startup;

import java.util.ArrayList;
import java.util.List;

public class Task5 extends AndroidStartup<Void> {

    static List<Class<? extends Startup<?>>> depends;

    static {
        depends = new ArrayList<>();
        depends.add(Task3.class);
        depends.add(Task4.class);
    }

    @Override
    public Void create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper()
                ? "主线程: " : "子线程: ";
        Log.i("TAG", t + " Task5：学习OkHttp");
        SystemClock.sleep(500);
        Log.i("TAG", t + " Task5：掌握OkHttp");
        return null;
    }


    //执行此任务需要依赖哪些任务
    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return depends;
    }

}
