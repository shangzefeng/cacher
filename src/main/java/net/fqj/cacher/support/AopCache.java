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

import java.lang.reflect.Method;
import net.fqj.cacher.annotation.Cache;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fqj.cacher.annotation.CacheClear;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;

/**
 *
 * @author Fsz
 */
@Aspect
public class AopCache {

    private final static Logger LOGGER = LoggerFactory.getLogger(AopCache.class);

    /**
     * cache管理器.
     */
    private CacheManage cacheManage;

    public AopCache(final CacheManage cacheManage) {
        this.cacheManage = cacheManage;
    }

    @Pointcut("@annotation(net.fqj.cacher.annotation.Cache)")
    private void cache() {
    }

    @Pointcut("@annotation(net.fqj.cacher.annotation.CacheClear)")
    private void cacheClear() {
    }

    /**
     * cache.
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("cache()")
    @SuppressWarnings("UnusedAssignment")
    public Object cacheAround(ProceedingJoinPoint pjp) throws Throwable {

        final MethodSignature ms = (MethodSignature) pjp.getSignature();
        final Method method = ms.getMethod();

        final Cache cache = method.getAnnotation(Cache.class);

        final IKeyGenerator iKeyGenerator = cache.generator().newInstance();
        final String key = iKeyGenerator.generatKey(cache.spel(), pjp.getArgs());
        Object result = this.cacheManage.read(key, cache.valueType(), cache.expire());
        if (null == result) {
            result = pjp.proceed();
            this.cacheManage.write(key, result, cache.expire());
        }

        return result;
    }

    /**
     * 缓存失效.
     *
     * @param joinPoint
     */
    @SuppressWarnings("unchecked")
    @AfterReturning("cacheClear()")
    public void logInfo(JoinPoint joinPoint) throws Exception {
        Object[] object = joinPoint.getArgs();
        final MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        final CacheClear cacheClear = ms.getMethod().getAnnotation(CacheClear.class);
        final IKeyGenerator iKeyGenerator
                = cacheClear.generator().newInstance();
        final String key = iKeyGenerator.generatKey(cacheClear.spel(), object);
        this.cacheManage.remove(key);
    }
}
