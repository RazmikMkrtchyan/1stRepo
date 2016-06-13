package igniteTest;

import org.apache.ignite.configuration.CacheConfiguration;
import org.junit.internal.Throwables;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.Factory;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.EternalExpiryPolicy;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Created by Razmik.Mkrtchyan on 5/31/2016.
 */
public class IgniteCacheContainer {
	private static final ConcurrentHashMap<String, Supplier<Cache<Integer, String>>> entityCacheSuppliers = new ConcurrentHashMap<>();

	private final CacheManager cacheManager;

	public IgniteCacheContainer(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
		abstractCacheConfiguration=SpringContextHolder.applicationContext
				.getBean("abstractConfiguration", CacheConfiguration.class);
	}

	private  CacheConfiguration<Integer, String> abstractCacheConfiguration;

	private void createCache(String cacheName, Factory<? extends CacheLoader<Integer, String>> factory) {
		if (cacheExists(cacheName)) {
			throw new IllegalArgumentException(
					String.format("cache for %s category has already been initialized", cacheName));
		}
		cacheManager.createCache(cacheName,
				new CacheConfiguration<>(abstractCacheConfiguration).setName(cacheName)
						.setCacheLoaderFactory(factory)
						.setReadThrough(true)
						.setExpiryPolicyFactory(EternalExpiryPolicy.factoryOf()));
	}

	private boolean cacheExists(String key) {
		for (String exisitingKey : cacheManager.getCacheNames()) {
			if (exisitingKey != null && exisitingKey.equals(key)) {
				return true;
			}
		}
		return false;
	}

	private void createCache(String cacheName) {
		createCache(cacheName, (Factory<CacheLoader<Integer, String>>) () -> new CacheLoader<Integer, String>() {
			@Override
			public String load(Integer key)
					throws CacheLoaderException {
				return "s" + key;
			}

			@Override
			public Map<Integer, String> loadAll(Iterable<? extends Integer> keys)
					throws CacheLoaderException {
				return Collections.emptyMap();
			}
		});
	}

	public Cache<Integer, String> getCacheByName(String cacheName) {
		return cacheManager.getCache(cacheName);
	}
}
