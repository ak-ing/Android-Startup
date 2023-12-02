# Android-Startup
Android应用组件初始化框架，简单、高效的完成组件的初始化工作，可调整组件启动顺序
通过拓扑排序来完成组件依赖关系的启动顺序

## 使用
### 定义初始化任务
继承自 `AndroidStartup`
``` java
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
```


### Manifest中添加配置
```xml
        <provider
            android:name=".initializer.StartupProvider"
            android:authorities="${applicationId}.android_startup"
            android:exported="false">
            <meta-data
                android:name="com.aking.androidstartup.tasks.Task5"
                android:value="android.startup" />
        </provider>
```


即可自动完成任务的初始化工作
