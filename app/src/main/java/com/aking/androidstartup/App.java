package com.aking.androidstartup;

import android.app.Application;
import android.content.Context;

import com.aking.androidstartup.manager.StartupManager;
import com.aking.androidstartup.startup.AndroidStartup;
import com.aking.androidstartup.startup.TaskStartup;
import com.aking.androidstartup.tasks.Task1;
import com.aking.androidstartup.tasks.Task2;
import com.aking.androidstartup.tasks.Task3;
import com.aking.androidstartup.tasks.Task4;

/**
 * Created by Rick at 2023-11-06 20:12.
 * Description:
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

       // new StartupManager.Builder()
       //         .addStartup(new TaskStartup<>(context -> {
       //             return "";
       //         }))
       //         .addStartup(new Task4())
       //         .addStartup(new Task3())
       //         .addStartup(new Task2())
       //         .addStartup(new Task1())
       //         .build(this)
       //         .start().await();
//
       // new AndroidStartup<String>() {
       //     @Override
       //     public String create(Context context) {
       //         return null;
       //     }
       // };
//
       // new TaskStartup<>(context -> {
       //     return "test";
       // });
//
       // new TaskStartup<>(context -> {
       //     return 100;
       // });
    }
}
