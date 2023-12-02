package com.aking.androidstartup.startup;

/**
 * Created by Rick at 2023-11-06 10:44.
 * Description: 任务结果包装类
 */
public class Result<T> {

    public T data;

    public Result(T data) {
        this.data = data;
    }
}
