package com.ozz.cache.service;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class MyCaffeineDemo<K, V> {

  /**
   * 手动加载：不存在返回空
   */
  public Cache<K, V> cache() {
    return Caffeine.newBuilder()
        .maximumSize(10_000)
        .expireAfterWrite(Duration.ofHours(2))
//        .expireAfterAccess(1, TimeUnit.HOURS)
//        .removalListener((key,value,cause) -> System.out.println(String.format("key %s was removed (%s)", key, cause)))
        .build();
  }

  /**
   * 同步加载：不存在时自动加载
   */
  public Cache<K, V> loadingCache(Function<K, V> loader) {
    return Caffeine.newBuilder()
        .maximumSize(10_000)
//        .refreshAfterWrite(Duration.ofHours(2))
        .expireAfterWrite(2, TimeUnit.HOURS)
        .build(key -> loader.apply(key));
  }

  /**
   * 异步加载
   */
  public AsyncLoadingCache<K, V> asyncLoadingCache(Function<K, V> loader) {
    return Caffeine
        .newBuilder()
        .maximumSize(10_000)
        .expireAfterWrite(2, TimeUnit.HOURS)
        .buildAsync((key) -> loader.apply(key));
  }
}
