package com.bex.cpi_search.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Configuration class for setting up Redis in a Spring application. This class defines the
 * necessary beans for connecting to a Redis server and configuring a RedisTemplate for interacting
 * with Redis data.
 */
@Configuration
public class RedisConfig {

  /** The Redis server host address, injected from the application properties. */
  @Value("${spring.redis.host}")
  private String redisHost;

  /** The Redis server port number, injected from the application properties. */
  @Value("${spring.redis.port}")
  private int redisPort;

  /** The Redis server password, injected from the application properties. */
  @Value("${spring.redis.password}")
  private String redisPassword;

  /**
   * Creates and configures a LettuceConnectionFactory for Redis based on the specified host, port,
   * and password.
   *
   * @return a LettuceConnectionFactory configured with the Redis server details
   */
  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
    redisConfig.setHostName(redisHost);
    redisConfig.setPort(redisPort);
    redisConfig.setPassword(redisPassword);
    return new LettuceConnectionFactory(redisConfig);
  }

  /**
   * Configures a RedisTemplate with a connection factory and serializers for keys and values. The
   * keys are serialized as strings, and the values are serialized as JSON using Jackson.
   *
   * @return a RedisTemplate configured for String keys and JSON-serialized values
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    return template;
  }
}
