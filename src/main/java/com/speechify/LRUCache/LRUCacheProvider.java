package com.speechify.LRUCache;
import java.util.*;

/**
 *
 * Use the provided com.speechify.LRUCacheProviderTest in `src/test/java/LruCacheTest.java` to validate your
 * implementation.
 *
 * You may:
 *  - Read online API references for Java standard library or JVM collections.
 * You must not:
 *  - Read guides about how to code an LRU cache.
 */

public class LRUCacheProvider {
    public static <T> LRUCache<T> createLRUCache(CacheLimits options) {
        if(options== null)
        {
            throw new UnsupportedOperationException("Implement this function");
        }
        final int maxsize=options.getMaxItemsCount();
        final int intitialcapacity=Math.max(1,maxsize);

        return new LRUCache<T>() {
            private final Map<String, T> map = new LinkedHashMap<String, T>(intitialcapacity, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, T> eldest) {
                    return size() > maxsize;
                }
            };

            @Override
            public synchronized T get(String key) {
                return map.get(key);
            }

            @Override
            public synchronized void set(String key, T value) {
                map.put(key, value);
            }
        };
    }
}
