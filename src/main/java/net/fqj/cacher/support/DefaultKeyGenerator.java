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

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 *
 * 缓存生成器.
 *
 * @author Fsz
 * @since Jun 21 2018
 */
public class DefaultKeyGenerator implements IKeyGenerator {

    /**
     * abc:{1}:def{2}, 其中{1}表式为方法中的第一个参数{2}表式方法中的第二个参数.
     *
     * @param spel
     * @param args
     * @return
     */
    @Override
    public String generatKey(String spel, Object[] args) throws CacheException {
        final StringBuilder sb = new StringBuilder();
        if (StringUtils.isBlank(spel)) {
            return sb.toString();
        }
        try {
            final Map<String, Integer> list = getParamMap(spel);

            for (Map.Entry<String, Integer> entry : list.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                if (value == -1) {
                    sb.append(key);
                    continue;
                }
                sb.append(key).append(args[value - 1]);
            }
        } catch (final Exception e) {
            throw new CacheException(e.getMessage(), e);
        }
        return sb.toString();
    }

    private Map<String, Integer> getParamMap(String spel) {
        final Map<String, Integer> map = new LinkedHashMap<>();
        while (!StringUtils.isBlank(spel)) {

            String[] prefix = StringUtils.split(spel, "{", 2);
            if (prefix.length == 1) {
                map.put(spel, -1);
                break;
            }
            spel = prefix[1];

            String[] suffix = StringUtils.split(spel, "}", 2);
            map.put(prefix[0], Integer.valueOf(suffix[0]));
            spel = suffix.length == 1 ? "" : suffix[1];
        }
        return map;
    }

}
