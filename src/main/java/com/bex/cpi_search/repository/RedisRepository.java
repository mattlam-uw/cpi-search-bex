package com.bex.cpi_search.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repository class for interacting with Redis to store and retrieve documents. This class handles
 * the saving and fetching of documents in a Redis hash using a combination of year and month as the
 * key.
 */
@Repository
public class RedisRepository {

  /** RedisTemplate for performing operations on Redis. */
  @Autowired private RedisTemplate<String, Object> redisTemplate;

  /** Key used for storing documents in the Redis hash. */
  private static final String DOCUMENTS_KEY = "documents";

  /**
   * Saves a document in Redis using a combination of year and month as the key.
   *
   * @param year the year associated with the document
   * @param month the month associated with the document
   * @param document the document to be saved
   */
  public void saveDocument(final String year, final String month, final String document) {
    String key = generateKey(year, month);
    redisTemplate.opsForHash().put(DOCUMENTS_KEY, key, document);
  }

  /**
   * Retrieves a document from Redis based on the provided year and month.
   *
   * @param year the year associated with the document
   * @param month the month associated with the document
   * @return the retrieved document as a String, or null if not found
   */
  public String getDocument(final String year, final String month) {
    String key = generateKey(year, month);
    return (String) redisTemplate.opsForHash().get(DOCUMENTS_KEY, key);
  }

  /**
   * Generates a Redis key based on the year and month.
   *
   * @param year the year to be used in the key
   * @param month the month to be used in the key
   * @return a String representing the key in the format "year:month"
   */
  private String generateKey(final String year, final String month) {
    return year + ":" + month;
  }
}
