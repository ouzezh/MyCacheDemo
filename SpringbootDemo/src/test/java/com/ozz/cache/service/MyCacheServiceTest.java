package com.ozz.cache.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

@SpringBootTest
class MyCacheServiceTest {
  @Autowired
  MyCacheService myCacheService;
  @Autowired
  CacheManager cacheManager;
  @Autowired
  RedisTemplate redisTemplate;

  @Test
  void getData() {
    int days = 1, hours = 3;
    String ldt = myCacheService.getData(days, hours);
    sleep(5);
    Assert.isTrue(ldt.equals(myCacheService.getData(days, hours)), StringUtils.EMPTY);
  }

  @Test
  void refreshAndGetData() {
    int days = 1, hours = 2;
    String ldt = myCacheService.getData(days, hours);
    sleep(5);
    Assert.isTrue(!ldt.equals(myCacheService.refreshAndGetData(days, hours)), StringUtils.EMPTY);
  }

  @Test
  void delData() {
    int days = 2, hours = 2;
    String ldt = myCacheService.getData(days, hours);
    sleep(5);
    myCacheService.delData(days, hours);
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