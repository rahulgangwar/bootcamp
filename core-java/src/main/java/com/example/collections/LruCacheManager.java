package com.example.collections;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

/** Oldest entry should be removed when cache size is exceeded */
@Getter
public class LruCacheManager {
  private static Integer MAX = 3;
  private Map<Integer, Node> cache =
      new LinkedHashMap<Integer, Node>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
          return size() > MAX;
        }

        /**
         * Get the value for key Also add that key value pair to the very end of map i.e place for
         * the latest entry
         */
        @Override
        public Node get(Object key) {
          Node value = super.get(key);
          // Removing and putting
          this.remove(key);
          this.put((Integer) key, value);
          return value;
        }
      };

  public static void main(String[] args) {
    LruCacheManager lruCacheManager = new LruCacheManager();
    lruCacheManager.cache.put(1, new Node(1));
    lruCacheManager.cache.put(2, new Node(2));
    lruCacheManager.cache.put(3, new Node(3));
    lruCacheManager.cache.put(4, new Node(4));

    System.out.println(lruCacheManager.cache);
    lruCacheManager.cache.get(2); // 2 should be the last entry after this
    System.out.println(lruCacheManager.cache);
    lruCacheManager.cache.put(
        5, new Node(5)); // 5 should be inserted and the eldest entry should be removed
    System.out.println(lruCacheManager.cache);
  }
}
