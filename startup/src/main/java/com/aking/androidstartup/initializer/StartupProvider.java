package com.aking.androidstartup.initializer;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aking.androidstartup.manager.StartupManager;
import com.aking.androidstartup.startup.Startup;

import java.util.List;

/**
 * Created by Rick at 2023-11-07 0:34.<p>
 * Description: 自动初始化，只需配置有向无环图中最后一个节点，自动向上查找
 * <blockquote><pre>
 *
 *     < provider
 *          android:name=".initializer.StartupProvider"
 *          android:authorities="${applicationId}.android_startup"
 *          android:exported="false">
 *          <meta-data
 *              android:name="com.aking.androidstartup.tasks.Task5"
 *              android:value="android.startup" />
 *      < /provider>
 *
 * </pre></blockquote>
 */
public class StartupProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        long time = System.currentTimeMillis();
        try {
            List<Startup<?>> startups = StartupInitializer.discoverAndInitial(getContext(), getClass().getName());
            new StartupManager.Builder()
                    .addAllStartup(startups)
                    .build(getContext())
                    .start()
                    .await();
            Log.i("StartupProvider", "Startup finished: " + (System.currentTimeMillis() - time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
