package com.example;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Solution {
    public static void main(String[] args) throws Exception {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        executor.scheduleAtFixedRate(() -> System.out.println("Task running"), 0, 5, TimeUnit.SECONDS);

    }

    public static int getCapacity(HashMap<?, ?> map) throws Exception {
        Field tableField = HashMap.class.getDeclaredField("table");
        tableField.setAccessible(true);
        Object[] table = (Object[]) tableField.get(map);
        return table.length;
    }


    public interface A<T extends Object>{
        public void fun(T x);
    }

    public static class B implements A<Integer>{
        @Override
        public void fun(Integer x) {

        }
    }
}

