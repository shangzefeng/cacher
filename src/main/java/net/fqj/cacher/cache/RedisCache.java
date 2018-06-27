/*
 * Copyright (c) 2018, net.fqj
 *
 * All rights reserved.
 */
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.fqj.cacher.cache;

import net.fqj.cacher.support.CacheException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * redis cache.
 *
 * @author Fsz
 * @since Jun 21 2018
 */
public class RedisCache implements ICache {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCache.class);

    /**
     * redis connection pool.
     */
    private final JedisPool jedisPool;

    public RedisCache(final JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public byte[] read(String key, final int expireTime) throws CacheException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (null == jedis) {
                throw new CacheException("get redis connection exception");
            }
            final byte[] keyByte = key.getBytes("utf-8");
            final byte[] by = jedis.get(keyByte);
            jedis.expire(keyByte, expireTime);
            return by;
        } catch (Exception e) {
            throw new CacheException("loading cache data exception", e);
        } finally {
            try {
                if (null != jedis) {
                    jedis.close();
                }
            } catch (Exception e) {
                throw new CacheException("close jedis exception", e);
            }
        }
    }

    @Override
    public void write(String key, final byte[] value, int expireTime) throws CacheException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (null == jedis) {
                throw new CacheException("get redis connection exception");
            }
            final byte[] keyByte = key.getBytes("utf-8");
            jedis.set(keyByte, value);
            jedis.expire(keyByte, expireTime);

        } catch (Exception e) {
            throw new CacheException("write cache data exception", e);
        } finally {
            try {
                if (null != jedis) {
                    jedis.close();
                }
            } catch (Exception e) {
                throw new CacheException("close jedis exception", e);
            }
        }
    }

    /**
     * 缓存失效.
     *
     * @param key
     */
    @Override
    public void remove(String key) {
        Jedis jedis = null;
        try {
            if (StringUtils.isBlank(key)) {
                return;
            }
            jedis = jedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            LOGGER.error("cache key vaild exception", e);
        } finally {
            try {
                if (null != jedis) {
                    jedis.close();
                }
            } catch (Exception e) {
                LOGGER.error("close jedis exception", e);
            }
        }
    }
}
