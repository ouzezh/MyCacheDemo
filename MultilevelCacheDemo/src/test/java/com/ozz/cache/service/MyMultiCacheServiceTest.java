package com.ozz.cache.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.util.Assert;

@SpringBootTest
class MyMultiCacheServiceTest {
  @Autowired
  MyMultiCacheService myMultiCacheService;
  @Autowired
  CacheManager cacheManager;

  @Test
  void getData() {
    int days = 2, hours = 1;
    String ldt = myMultiCacheService.getData(days, hours);
    sleep(5);
    Assert.isTrue(ldt.equals(myMultiCacheService.getData(days, hours)), StringUtils.EMPTY);
  }

  @Test
  void refreshAndGetData() {
    int days = 2, hours = 2;
    String ldt = myMultiCacheService.getData(days, hours);
    sleep(5);
    Assert.isTrue(!ldt.equals(myMultiCacheService.refreshAndGetData(days, hours)), StringUtils.EMPTY);
  }

  @Test
  void delCache() {
    int days = 2, hours = 3;
    String ldt = myMultiCacheService.getData(days, hours);
    sleep(5);
    myMultiCacheService.delCache(days, hours);
    Assert.isTrue(!ldt.equals(myMultiCacheService.getData(days, hours)), StringUtils.EMPTY);
  }

  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}