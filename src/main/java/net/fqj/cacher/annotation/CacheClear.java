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
package net.fqj.cacher.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.fqj.cacher.support.DefaultKeyGenerator;
import net.fqj.cacher.support.IKeyGenerator;

/**
 * 会话失效.
 *
 * @author Fsz
 * @since Jun 21 2018
 */
@Target(value = {ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface CacheClear {

    /**
     * 缓存key的表达式.
     *
     * @return
     */
    String spel() default "";

    /**
     * 解析生成key的类.
     *
     * @return
     */
    public Class<? extends IKeyGenerator> generator() default DefaultKeyGenerator.class;
}
