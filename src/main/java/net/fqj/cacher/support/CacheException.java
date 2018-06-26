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
 *
 * @author Fsz
 */
public class CacheException extends RuntimeException {

    public CacheException() {
        super();
    }

    public CacheException(String message) {
        super(message);
    }

    public CacheException(final String message, final Throwable e) {
        super(message, e);
    }

}
