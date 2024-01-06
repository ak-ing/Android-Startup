package com.aking.androidstartup.sort;

import com.aking.androidstartup.startup.Startup;
import com.aking.androidstartup.startup.StartupSortStore;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Rick at 2023-11-04 21:47.
 * Description: 实现任务拓扑排序
 */
public class TopologySort {

    public static StartupSortStore sort(List<? extends Startup<?>> startupList) {
        //入度表
        Map<String, Integer> inDegreeMap = new HashMap<>();
        //0度表
        Deque<String> zeroDeque = new ArrayDeque<>();
        //任务表
        Map<String, Startup<?>> startupMap = new HashMap<>();
        //任务依赖表
        Map<Class<? extends Startup>, List<String>> startupChildrenMap = new HashMap<>();
        for (Startup<?> startup : startupList) {
            String uniqueKey = getUniqueKey(startup);
            startupMap.put(uniqueKey, startup);
            //记录每个任务的入度数（依赖的任务数）
            int dependenciesCount = startup.getDependenciesCount();
            inDegreeMap.put(uniqueKey, dependenciesCount);
            //记录入度为0的任务
            if (dependenciesCount == 0) {
                zeroDeque.offer(uniqueKey);
            } else {
                //遍历本任务的依赖任务，生成任务依赖表
                for (Class<? extends Startup<?>> parent : startup.dependencies()) {
                    //记录这个父任务的所有子任务
                    startupChildrenMap.computeIfAbsent(parent, k -> new ArrayList<>()).add(uniqueKey);
                }
            }
        }

        //删除图这入度为0的顶点，并更新全图，最后完成排序
        List<Startup<?>> result = new ArrayList<>();
        //主线程任务排到后面启 动
        List<Startup<?>> main = new ArrayList<>();
        List<Startup<?>> threads = new ArrayList<>();

        //处理入度为0的任务
        while (!zeroDeque.isEmpty()) {
            String zeroKey = zeroDeque.poll();
            Startup<?> startup = startupMap.get(zeroKey);
            Class<? extends Startup> cls = startup.getClass();
            if (Objects.requireNonNull(startup).callCreateOnMainThread()) {
                main.add(startup);
            } else {
                threads.add(startup);
            }

            //删除此入度0的任务
            if (startupChildrenMap.containsKey(cls)) {
                List<String> childStartup = startupChildrenMap.get(cls);
                for (String childKey : childStartup) {
                    Integer num = inDegreeMap.get(childKey);
                    inDegreeMap.put(childKey, num - 1);
                    if (num - 1 == 0) {
                        zeroDeque.offer(childKey);
                    }
                }
            }
        }
        result.addAll(threads);
        result.addAll(main);
        return new StartupSortStore(result, startupMap, startupChildrenMap);
    }

    private static String getUniqueKey(Startup<?> startup) {
        return startup.getClass().getName() + "-" + startup.hashCode();
    }
}
