package org.example;

import sun.misc.Unsafe;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;
public class TestPlug {
    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException, InterruptedException {
        int n = 10 - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        System.out.println(n+1);
        HashMap<String,String> map=new HashMap<>(10);
        map.put("666","666");
        map.put("777","777");
        map.put("772","777");
        map.put("773","777");
        map.put("774","777");
        map.put("775","777");
        map.put("776","777");
        map.put("778","777");
        Class<? extends HashMap> aClass = map.getClass();
        Field entrySet = aClass.getDeclaredField("threshold");
        entrySet.setAccessible(true);
        Object o = entrySet.get(map);
        System.out.println(map.size());
        Thread t=new Thread(()->{throw new RuntimeException();});
        t.setUncaughtExceptionHandler((thread,e)->{
            try {
                throw  e;
            } catch (Throwable ex) {
                throw new RuntimeException(ex);
            }
        });
        t.start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("666");
        ThreadLocal threadLocal=new ThreadLocal<>();
        threadLocal.set("");
        threadLocal.get();
        Unsafe unsafe=Unsafe.getUnsafe();
        long address = unsafe.allocateMemory(9231l);
//        unsafe.freeMemory(address);
    }
}