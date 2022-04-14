package com.ozz.cache.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class MyCaffeineDemoTest {

  @Autowired
  MyCaffeineDemo<String, String> myCaffeineDemo;
  String key = "myKey";

  @Test
  void testCache() {
    Cache<String, String> cache = myCaffeineDemo.cache();
    // 检索一个entry，如果没有则为null
    String graph = cache.getIfPresent(key);
    Assert.isNull(graph, StrUtil.EMPTY);
    // 检索一个entry，如果entry为null，则通过key创建一个entry并加入缓存
    graph = cache.get(key, k -> queryData(key));
    Assert.notNull(graph, StrUtil.EMPTY);
    // 插入或更新一个实体
    cache.put(key, graph);
    // 移除一个实体
    cache.invalidate(key);
  }

  @Test
  void testLoadingCache() {
    Cache<String, String> cache = myCaffeineDemo.loadingCache(key -> queryData(key));
    Assert.notNull(cache, "loading fail");
  }

  @Test
  void testAsyncLoadingCache() throws ExecutionException, InterruptedException {
    AsyncLoadingCache<String, String> cache = myCaffeineDemo
        .asyncLoadingCache(key -> queryData(key));
    CompletableFuture<String> cf = cache.get(key);
    String v = cf.get();
    StaticLog.info(v);
    Assert.notNull(v, "loading fail");
  }

  private String queryData(String key) {
    return "myValue_" + LocalDateTime.now().toString();
  }
}
