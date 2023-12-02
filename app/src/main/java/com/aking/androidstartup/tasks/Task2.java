package com.aking.androidstartup.tasks;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import com.aking.androidstartup.startup.AndroidStartup;
import com.aking.androidstartup.startup.Startup;

import java.util.ArrayList;
import java.util.List;

public class Task2 extends AndroidStartup<Void> {
    static List<Class<? extends Startup<?>>> depends;

    static {
        depends = new ArrayList<>();
        depends.add(Task1.class);
    }

    @Nullable
    @Override
    public Void create(Context context) {
        Log.i("TAG", " Task2：学习Socket");
        SystemClock.sleep(800);
        Log.i("TAG", " Task2：掌握Socket");
        return null;
    }

    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return depends;
    }
}
