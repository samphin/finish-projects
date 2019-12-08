package com.ryit.security.shiro;

import com.ryit.commons.constants.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.Jedis;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : samphin
 * @since : 2019-10-16 17:02:57
 */
@Slf4j
public class ShiroRedisCache<K, V> implements Cache<K, V> {


    /**
     * key前缀
     */
    private static final String REDIS_SHIRO_CACHE_KEY_PREFIX = RedisConstants.PREFIX_SHIRO_CACHE;

    /**
     * cache name
     */
    private String name;

    /**
     * jedis
     */
    private Jedis jedis;

    /**
     * 序列化工具
     */
    private RedisSerializer serializer = new JdkSerializationRedisSerializer();

    /**
     * 存储key的redis.list的key值
     */
    private String keyListKey;

    public ShiroRedisCache(String name, Jedis jedis) {
        this.name = name;
        this.jedis = jedis;
        this.keyListKey = "redis.shiro.cache.key_" + name;
    }

    @Override
    public V get(K key) throws CacheException {
        log.debug("shiro redis cache get.{} K={}", name, key);
        V result = (V) serializer.deserialize(jedis.get(serializer.serialize(generateKey(key))));
        return result;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        log.debug("shiro redis cache put.{} K={} V={}", name, key, value);

        V result = (V) serializer.deserialize(jedis.get(serializer.serialize(generateKey(key))));

        jedis.set(serializer.serialize(generateKey(key)), serializer.serialize(value));

        jedis.lpush(serializer.serialize(keyListKey), serializer.serialize(generateKey(key)));

        return result;
    }

    @Override
    public V remove(K key) throws CacheException {
        log.debug("shiro redis cache remove.{} K={}", name, key);
        V result = (V) serializer.deserialize(jedis.get(serializer.serialize(generateKey(key))));


        jedis.expireAt(serializer.serialize(generateKey(key)), 0);

        jedis.lrem(serializer.serialize(keyListKey), 0, serializer.serialize(key));

        return result;
    }

    @Override
    public void clear() throws CacheException {
        log.debug("shiro redis cache clear.{}", name);
        Long length = jedis.llen(serializer.serialize(keyListKey));
        if (0 == length) {
            return;
        }

        List<byte[]> keyList = jedis.lrange(serializer.serialize(keyListKey), 0, length - 1);
        for (byte[] key : keyList) {
            jedis.expireAt(key, 0);
        }

        jedis.expireAt(serializer.serialize(keyListKey), 0);
        keyList.clear();
    }

    @Override
    public int size() {
        log.debug("shiro redis cache size.{}", name);
        int length = Math.toIntExact(jedis.llen(serializer.serialize(keyListKey)));
        return length;
    }

    @Override
    public Set keys() {
        log.debug("shiro redis cache keys.{}", name);
        RedisConnection redisConnection = null;
        Set resultSet = null;
        Long length = jedis.llen(serializer.serialize(keyListKey));
        if (0 == length) {
            return resultSet;
        }
        List<byte[]> keyList = jedis.lrange(serializer.serialize(keyListKey), 0, length - 1);
        resultSet = keyList.stream().map(bytes -> serializer.deserialize(bytes)).collect(Collectors.toSet());
        return resultSet;
    }

    @Override
    public Collection values() {
        return null;
    }

    /**
     * 重组key
     * 区别其他使用环境的key
     *
     * @param key
     * @return
     */
    private String generateKey(K key) {
        return REDIS_SHIRO_CACHE_KEY_PREFIX + name + "_" + key;
    }
}
