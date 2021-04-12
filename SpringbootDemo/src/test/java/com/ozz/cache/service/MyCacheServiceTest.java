package com.ozz.cache.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.util.Assert;

@SpringBootTest
class MyCacheServiceTest {
  @Autowired
  MyCacheService myCacheService;
  @Autowired
  CacheManager cacheManager;

  @Test
  void getData() {
    Integer days = 1, hours = 1;
    String ldt = myCacheService.getData(days, hours);
    sleep(5);
    Assert.isTrue(ldt.equals(myCacheService.getData(days, hours)), StringUtils.EMPTY);
  }

  @Test
  void refreshAndGetData() {
    int days = 1, hours = 3;
    String ldt = myCacheService.getData(days, hours);
    sleep(5);
    Assert.isTrue(!ldt.equals(myCacheService.refreshAndGetData(days, hours)), StringUtils.EMPTY);
  }

  @Test
  void delCache() {
    int days = 1, hours = 4;
    String ldt = myCacheService.getData(days, hours);
    sleep(5);
    myCacheService.delCache(days, hours);
    Assert.isTrue(!ldt.equals(myCacheService.getData(days, hours)), StringUtils.EMPTY);
  }

  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}