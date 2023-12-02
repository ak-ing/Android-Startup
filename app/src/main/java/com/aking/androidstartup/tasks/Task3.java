
package com.aking.androidstartup.tasks;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import com.aking.androidstartup.startup.AndroidStartup;
import com.aking.androidstartup.startup.Startup;

import java.util.ArrayList;
import java.util.List;

public class Task3 extends AndroidStartup<Void> {

    static List<Class<? extends Startup<?>>> depends;

    static {
        depends = new ArrayList<>();
        depends.add(Task1.class);
    }

    @Nullable
    @Override
    public Void create(Context context) {
        Log.i("TAG", " Task3：学习设计模式");
        SystemClock.sleep(2_000);
        Log.i("TAG", " Task3：掌握设计模式");
        return null;
    }


    //    执行此任务需要依赖哪些任务
    @Nullable
    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return depends;
    }


}
