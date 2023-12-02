package com.aking.androidstartup.tasks;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.aking.androidstartup.startup.AndroidStartup;
import com.aking.androidstartup.startup.Startup;

import java.util.ArrayList;
import java.util.List;

public class Task4 extends AndroidStartup<Void> {

    static List<Class<? extends Startup<?>>> depends;

    static {
        depends = new ArrayList<>();
        depends.add(Task2.class);
    }

    @Override
    public Void create(Context context) {
        Log.i("TAG", " Task4：学习Http");
        SystemClock.sleep(1_000);
        Log.i("TAG", " Task4：掌握Http");
        return null;
    }

    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return depends;
    }

}
