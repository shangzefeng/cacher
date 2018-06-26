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

/**
 * cache接口定义 .
 *
 * @author Fsz
 * @since Jun 21 2018
 */
public interface ICache {

    public byte[] read(final String key, final int expireTime) throws CacheException;

    public void write(final String key, final byte[] value,
            final int expireTime) throws CacheException;

    public void remove(final String key) throws CacheException;
}
