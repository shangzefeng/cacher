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

import com.caucho.hessian.io.Deflation;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import net.fqj.cacher.support.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * hessian2+deflat压缩序列化及反序列化.
 *
 * @author Fsz
 * @since
 */
public class Hessian2Serializable implements CacheSerializable {

    private final static Logger LOGGER = LoggerFactory.getLogger(Hessian2Serializable.class);

    /**
     * 序列化字符编码.
     */
    private String charSet = "utf-8";

    /**
     * top 序列化字符编码.
     *
     * @return the charSet
     */
    public String getCharSet() {
        return charSet;
    }

    /**
     * 序列化字符编码.
     *
     * @param charSet the charSet to set
     */
    public void setCharSet(final String charSet) {
        this.charSet = charSet;
    }

    /**
     * 序列化.
     *
     * @param object
     * @return
     */
    public byte[] serialize(final Object object) throws CacheException {
        ByteArrayOutputStream baos = null;
        Hessian2Output hessian2Output = null;
        try {
            baos = new ByteArrayOutputStream();
            hessian2Output = new Hessian2Output(baos);
            final Deflation deflation = new Deflation();

            hessian2Output = deflation.wrap(hessian2Output);
            hessian2Output.writeObject(object);
            hessian2Output.close();

            final byte[] by = baos.toByteArray();
            return by;
        } catch (Exception e) {
            throw new CacheException("序列化异常", e);
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                }
            } catch (Exception e) {
                LOGGER.error("close bytearrayoutputstream exception", object);
            }
        }

    }

    /**
     * 反序列化.
     *
     * @param <T>
     * @param by
     * @param clazz
     * @return
     */
    public <T> T deserialize(final byte[] by, Class<T> clazz) throws CacheException {
        ByteArrayInputStream bis = null;
        Hessian2Input hessian2Input = null;

        try {
            if (null == by || by.length == 0) {
                throw new Exception("反序列化的by为空");
            }
            bis = new ByteArrayInputStream(by);

            final Deflation deflation = new Deflation();
            hessian2Input = new Hessian2Input(bis);

            hessian2Input = deflation.unwrap(hessian2Input);
            return (T) hessian2Input.readObject(clazz);
        } catch (Exception e) {
            throw new CacheException("反序列化异常", e);
        } finally {
            try {
                if (null != bis) {
                    bis.close();
                }
            } catch (final Exception e) {
                LOGGER.error("close bytearrayinputstream exception", e);
            }
            try {
                if (null != hessian2Input) {
                    hessian2Input.close();
                }
            } catch (final Exception e) {
                LOGGER.error("close hessian2 exception", e);
            }
        }

    }
}
