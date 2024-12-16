package com.example.taskproject.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;


@Slf4j
public class CacheUtil {

    private static CacheManager cacheManager;

    public static void clearAllCaches() {
        if (cacheManager != null) {
            cacheManager.getCacheNames().forEach(cacheName -> {
                if (cacheManager.getCache(cacheName) != null) {
                    cacheManager.getCache(cacheName).clear();
                }
            });
        }
    }

    // очистка кеша
    public static void main(String[] args) {
        try {
            clearAllCaches();
        } catch (Exception e) {
            System.out.println("Cache is not cleared" + e.getMessage());
        }

    }
}
