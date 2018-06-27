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
package net.fqj.cacher.serializable;

import net.fqj.cacher.support.CacheException;

/**
 *
 * @author Fsz
 */
public interface CacheSerializable {

    /**
     * 序列化.
     *
     * @param object
     * @return
     */
    public byte[] serialize(final Object object) throws CacheException;

    /**
     * 反序列化.
     *
     * @param <T>
     * @param by
     * @param clazz
     * @return
     */
    public <T> T deserialize(final byte[] by, Class<T> clazz) throws CacheException;
}
