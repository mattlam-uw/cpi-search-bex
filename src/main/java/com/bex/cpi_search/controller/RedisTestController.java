package com.bex.cpi_search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for testing Redis connectivity. This controller exposes an endpoint to test the Redis
 * connection.
 */
@RestController
@RequestMapping("/redis-test")
public final class RedisTestController {

  /**
   * RedisTemplate used for interacting with Redis. This template is used to set and get values from
   * the Redis data store.
   */
  @Autowired private RedisTemplate<String, Object> redisTemplate;

  /**
   * Tests the Redis connection by setting and retrieving a test value.
   *
   * @return A message indicating whether the Redis connection is working or not.
   */
  @GetMapping
  public String testRedis() {
    try {
      // Set a test value
      redisTemplate.opsForValue().set("test_key", "test_value");

      // Get the test value
      Object value = redisTemplate.opsForValue().get("test_key");

      if ("test_value".equals(value)) {
        return "Redis connection is working. Test key was set and retrieved successfully.";
      } else {
        return "Redis connection is working, but the test key value is incorrect.";
      }
    } catch (Exception e) {
      return "Failed to connect to Redis: " + e.getMessage();
    }
  }
}
