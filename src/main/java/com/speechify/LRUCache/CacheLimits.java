package com.speechify.LRUCache;

public class CacheLimits {
    /**
     * @property maxItemsCount
     * Maximum count of items (*inclusive*) that this cache is allowed to contain.
     */
    public int maxItemsCount;
    public CacheLimits() {
    }
    public CacheLimits(int maxItemsCount) {
        this.maxItemsCount = maxItemsCount;
    }
}