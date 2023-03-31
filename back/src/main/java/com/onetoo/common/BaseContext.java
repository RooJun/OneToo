package com.onetoo.common;

/**
 * 基于ThreadLocal封装工具类，用于保存和获取当前登录用户的id
 * 中填充的的是当前线程的变量，该变量对其他线程而言是封闭且隔离的，
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
