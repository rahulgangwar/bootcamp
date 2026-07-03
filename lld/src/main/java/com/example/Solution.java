package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Solution {
    public class Singleton {
        private volatile Singleton instance;

        public Singleton getInstance() {
            if (instance == null) {
                synchronized (Singleton.class) {
                    if (instance == null) {
                        instance = new Singleton();
                    }
                }
            }
            new ArrayList<>().clone();
            return instance;
        }

    }
}
