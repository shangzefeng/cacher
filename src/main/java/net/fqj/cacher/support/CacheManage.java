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
package net.fqj.cacher.support;

import net.fqj.cacher.serializable.CacheSerializable;
import net.fqj.cacher.cache.ICache;

/**
 * cache管理器.
 *
 * @author Fsz
 */
public class CacheManage {

    private final ICache iCache;
    private final CacheSerializable cacheSerializable;

    public CacheManage(final ICache iCache, final CacheSerializable cacheSerializable) {
        this.iCache = iCache;
        this.cacheSerializable = cacheSerializable;
    }

    public Object read(final String key, Class<?> clazz, final int expireTime) {
        final byte[] by = iCache.read(key, expireTime);
        if (null == by || by.length == 0) {
            return null;
        }
        return cacheSerializable.deserialize(by, clazz);
    }

    public void write(final String key, final Object value,
            final int expireTime) {
        final byte[] by = cacheSerializable.serialize(value);
        if (null == by || by.length == 0) {
            return;
        }
        this.iCache.write(key, by, expireTime);
    }

    public void remove(final String key) {
        this.iCache.remove(key);
    }
}
