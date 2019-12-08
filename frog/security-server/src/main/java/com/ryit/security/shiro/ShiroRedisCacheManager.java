package com.ryit.security.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.crazycake.shiro.RedisManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author : samphin
 * @since : 2019-10-16 17:03:05
 */
@Slf4j
public class ShiroRedisCacheManager implements CacheManager, Destroyable {

    private RedisManager redisManager;

    public ShiroRedisCacheManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        log.debug("shiro redis cache manager get cache. name={} ", name);
        JedisPool jedisPool = redisManager.getJedisPool();
        Jedis jedis = jedisPool.getResource();
        return new ShiroRedisCache<>(name, jedis);
    }

    @Override
    public void destroy() throws Exception {
    }
}
