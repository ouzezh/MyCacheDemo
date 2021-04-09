package com.ozz.cache.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"cache1", "cache2"})
public class MyCacheService<K, V> {
  @Cacheable
  public String getData(Integer days, Integer hours) {
    return getData_(days, hours);
  }

  @CachePut
  public String refreshAndGetData(Integer days, Integer hours) {
    return getData_(days, hours);
  }

  @CacheEvict
  public void delData(Integer days, Integer hours) {
  }

  private String getData_(Integer days, Integer hours) {
    return LocalDateTime.now().plusDays(days).plusHours(hours).format(
        DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "_" + System.nanoTime();
  }
}