package com.example.app.scheduler;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheEvictionScheduler {

    private static final String CACHE_NAME = "exchangeRates";

    // This method will be triggered at midnight every day
    @Scheduled(cron = "0 0 0 * * ?")
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void evictCacheAtMidnight() {
        // This method doesn't need to have any implementation.
        // The @CacheEvict annotation will take care of evicting the cache.
    }
}