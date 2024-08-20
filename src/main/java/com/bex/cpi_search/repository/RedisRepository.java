package com.bex.cpi_search.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * Generic repository class for interacting with Redis to store and retrieve objects.
 *
 * @param <K> the type of the hash key
 * @param <V> the type of the hash value
 */
@Repository
public class RedisRepository<K, V> {

  @Autowired private RedisTemplate<String, V> redisTemplate;

  private static final String DOCUMENTS_KEY = "documents";

  /**
   * Saves a document in Redis using a hash.
   *
   * @param key the key for the hash
   * @param document the document to be saved
   */
  public void saveDocument(final K key, final V document) {
    HashOperations<String, K, V> hashOps = redisTemplate.opsForHash();
    hashOps.put(DOCUMENTS_KEY, key, document);
  }

  /**
   * Retrieves a document from Redis based on the provided key.
   *
   * @param key the key for the hash
   * @return the retrieved document, or null if not found
   */
  public V getDocument(final K key) {
    HashOperations<String, K, V> hashOps = redisTemplate.opsForHash();
    return hashOps.get(DOCUMENTS_KEY, key);
  }
}
