package com.ozz.cache.caffeine;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class CaffeineDemoTest {

  @Autowired
  CaffeineDemo<String, String> caffeineDemo;
  String key = "myKey";

  @Test
  void testCache() {
    Cache<String, String> cache = caffeineDemo.cache();
    // 检索一个entry，如果没有则为null
    String graph = cache.getIfPresent(key);
    Assert.isNull(graph, Strings.EMPTY);
    // 检索一个entry，如果entry为null，则通过key创建一个entry并加入缓存
    graph = cache.get(key, k -> createExpensiveGraph(key));
    Assert.notNull(graph, "");
    // 插入或更新一个实体
    cache.put(key, graph);
    // 移除一个实体
    cache.invalidate(key);
  }

  @Test
  void testLoadingCache() {
    Cache<String, String> cache = caffeineDemo.loadingCache(key -> createExpensiveGraph(key));
    Assert.notNull(cache, "loading fail");
  }

  @Test
  void testAsyncLoadingCache() throws ExecutionException, InterruptedException {
    AsyncLoadingCache<String, String> cache = caffeineDemo
        .asyncLoadingCache(key -> createExpensiveGraph(key));
    CompletableFuture<String> cf = cache.get(key);
    String v = cf.get();
    System.out.println(v);
    Assert.notNull(v, "loading fail");
  }

  private String createExpensiveGraph(String key) {
    return "myValue_" + LocalDateTime.now().toString();
  }
}
