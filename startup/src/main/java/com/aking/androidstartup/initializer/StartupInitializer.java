package com.aking.androidstartup.initializer;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.text.TextUtils;

import com.aking.androidstartup.startup.Startup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rick at 2023-11-07 0:54.
 * Description:
 */
public class StartupInitializer {
    public static final String META_VALUE = "android.startup";

    public static List<Startup<?>> discoverAndInitial(Context context, String providerName) throws Exception {
        //记录任务的结构图(有向无环图)
        Map<Class<? extends Startup>, Startup<?>> startups = new HashMap<>();

        //获取manifest contentProvider中的meta-data
        ComponentName provider = new ComponentName(context, providerName);
        ProviderInfo providerInfo = context.getPackageManager().getProviderInfo(provider,
                PackageManager.GET_META_DATA);
        Bundle metaData = providerInfo.metaData;

        for (String key : metaData.keySet()) {
            String value = metaData.getString(key);
            if (TextUtils.equals(META_VALUE, value)) {
                Class<?> startupClass = Class.forName(key); //com.aking.androidstartup.tasks.Task5
                if (Startup.class.isAssignableFrom(startupClass)) {//如果是Startup的子类就返回true
                    doInitialize((Startup<?>) startupClass.newInstance(), startups);
                }
            }
        }

        return new ArrayList<>(startups.values());
    }


    private static void doInitialize(Startup<?> startup, Map<Class<? extends Startup>, Startup<?>> startups) throws IllegalAccessException, InstantiationException {
        startups.put(startup.getClass(), startup);
        if (startup.getDependenciesCount() > 0) {
            //递归查找，初始化整个任务结构图
            for (Class<? extends Startup<?>> dependency : startup.dependencies()) {
                doInitialize(dependency.newInstance(), startups);
            }
        }
    }
}
