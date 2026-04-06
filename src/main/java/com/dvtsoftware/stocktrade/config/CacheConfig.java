package com.dvtsoftware.stocktrade.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AppCacheProperties.class, AppSecurityProperties.class})
public class CacheConfig {

    @Bean
    CacheManager cacheManager(AppCacheProperties cacheProperties) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
                "tradeById",
                "allTrades",
                "tradesByUser",
                "tradesByStockFilter",
                "stockPriceRange",
                "tradeSearch"
        );
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(cacheProperties.getMaximumSize())
                .expireAfterWrite(cacheProperties.getTtl())
                .recordStats());
        return cacheManager;
    }
}