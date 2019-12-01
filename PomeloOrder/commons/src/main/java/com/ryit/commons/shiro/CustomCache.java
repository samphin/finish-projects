package com.ryit.commons.shiro;

import com.ryit.commons.constant.JwtConstant;
import com.ryit.commons.constant.RedisConstants;
import com.ryit.commons.util.JwtUtil;
import com.ryit.commons.util.RedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;
import java.util.Set;

/**
 * @author : 刘修
 * @Date : 2019/8/21 15:46
 */
public class CustomCache<K, V> implements Cache<K, V> {

    @Value("${shiroCacheExpireTime}")
    private String shiroCacheExpireTime;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 缓存的key名称获取为shiro:cache:account
     *
     * @param key
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/9/4 18:33
     */
    private String getKey(Object key) {
        return String.format(RedisConstants.PREFIX_SHIRO_CACHE, JwtUtil.getClaim(key.toString(), JwtConstant.ACCOUNT));
    }

    /**
     * 获取缓存
     */
    @Override
    public Object get(Object key) throws CacheException {
        if (!redisUtil.hasKey(this.getKey(key))) {
            return null;
        }
        return redisUtil.get(this.getKey(key));
    }

    /**
     * 保存缓存
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        // 读取配置文件，获取Redis的Shiro缓存过期时间
        // PropertiesUtil.readProperties("config.properties");
        // String shiroCacheExpireTime =
        // PropertiesUtil.getProperty("shiroCacheExpireTime");
        // 设置Redis的Shiro缓存
        return redisUtil.set(this.getKey(key), value, Integer.parseInt(shiroCacheExpireTime));
    }

    /**
     * 移除缓存
     */
    @Override
    public Object remove(Object key) throws CacheException {
        if (!redisUtil.hasKey(this.getKey(key))) {
            return null;
        }
        redisUtil.remove(this.getKey(key));
        return null;
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {

    }

    /**
     * 缓存的个数
     */
    @Override
    public Set<K> keys() {
        return null;
    }

    /**
     * 获取所有的key
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * 获取所有的value
     */
    @Override
    public Collection<V> values() {
        return null;
    }

}
