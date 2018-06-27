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

/**
 * 缓存key生成器.
 *
 * @author Fsz
 */
public interface IKeyGenerator {

    /**
     * 生成缓存key的方法-解析spel表达式并生成key.
     *
     * @param spel 生成spel的表达式.
     * @param args 参数.
     * @return
     */
    String generatKey(final String spel, final Object[] args) throws CacheException;
}
