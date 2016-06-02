package com.targa.dev.formation.shiroj.security;

import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.util.LifecycleUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nebrass on 18/11/2015.
 */
public class EhCacheManagerTest {

    private EhCacheManager cacheManager;

    @Before
    public void setUp() {

        cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
    }

    @After
    public void tearDown() {
        LifecycleUtils.destroy(cacheManager);
    }

    @Test
    public void testCacheManagerCreationDuringInit() {
        CacheManager ehCacheManager = cacheManager.getCacheManager();
        assertNull(ehCacheManager);
        cacheManager.init();
        //now assert that an internal CacheManager has been created:
        ehCacheManager = cacheManager.getCacheManager();
        assertNotNull(ehCacheManager);
    }

    @Test
    public void testLazyCacheManagerCreationWithoutCallingInit() throws InterruptedException {
        CacheManager ehCacheManager = cacheManager.getCacheManager();
        assertNull(ehCacheManager);

        //don't call init here - the ehcache CacheManager should be lazily created
        //because of the default Shiro ehcache.xml file in the classpath.  Just acquire a cache:
        Cache<String, String> cache = cacheManager.getCache("test");

        //now assert that an internal CacheManager has been created:
        ehCacheManager = cacheManager.getCacheManager();
        assertNotNull(ehCacheManager);

        assertNotNull(cache);
        cache.put("hello", "world");
        String value = cache.get("hello");
        assertNotNull(value);
        assertEquals(value, "world");

        System.out.println("Conf: " + ehCacheManager.getConfiguration().getDiskStoreConfiguration().getPath());
        System.out.println("Name : " + ehCacheManager.getConfiguration().getName());

        ehCacheManager.shutdown();
    }

}