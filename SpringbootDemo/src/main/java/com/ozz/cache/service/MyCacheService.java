package com.ozz.cache.service;

import com.haozi.cache.core.AutoCache;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"cache1"})
public class MyCacheService<K, V> {
  @Cacheable(key="'my'+':'+#days+':'+#hours")
  public String getData(Integer days, Integer hours) {
    return getData_(days, hours);
  }

  @CachePut(key="'my'+':'+#days+':'+#hours")
  public String refreshAndGetData(Integer days, Integer hours) {
    return getData_(days, hours);
  }

  @CacheEvict(key="'my'+':'+#days+':'+#hours")
  public void delCache(Integer days, Integer hours) {
  }

  private String getData_(Integer days, Integer hours) {
    return LocalDateTime.now().plusDays(days).plusHours(hours).format(
        DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "_" + System.nanoTime();
  }

  @AutoCache(cacheName = "sxXX", localTTL = 60000, remoteTTL = 3600000, key = "#schoolId")
  public String testCAche(Integer schoolId) {
    System.out.println("refresh data");
    return schoolId + LocalDateTime.now().format(
        DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "_" + System.nanoTime();
  }
}
