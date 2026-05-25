package com.example.collections.maps;

import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapExample {
    public static class Demo {
        @Override
        protected void finalize() {
            System.out.println("Demo.finalize");
        }
    }

    public static void main(String[] args) throws Exception {
        Map<Demo, String> m = new WeakHashMap<>();
        Demo d = new Demo();

        m.put(d, " Hi ");

        System.out.println("Before GC: " + m);
        d = null;

        // On GC the entry is also removed from the weak hashmap since the key is a weak reference
        System.gc();

        //thread sleeps for 4 sec
        Thread.sleep(4000);

        System.out.println("After GC: " + m);
    }
}

