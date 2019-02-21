package com.oracle.vehicle.cache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.writer.CacheWriter;
import net.sf.ehcache.writer.CacheWriterFactory;

import java.util.Properties;

public class H2CacheWriterFactory extends CacheWriterFactory {

    @Override
    public CacheWriter createCacheWriter(Ehcache cache, Properties properties) {
        return new H2CacheWriter(cache);
    }

}
