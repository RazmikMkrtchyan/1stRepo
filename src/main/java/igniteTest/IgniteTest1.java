package igniteTest;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;

import javax.cache.configuration.Factory;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import java.util.Map;

/**
 * Created by Razmik.Mkrtchyan on 5/30/2016.
 */
public class IgniteTest1 {

	public static void main(String[] args) {
		Ignite ignite=Ignition.start("ignite-config.xml");
		IgniteCache<String, String> cache=ignite.getOrCreateCache("my_cache");
		IgniteCache<String, String> cache1=ignite.getOrCreateCache(new CacheConfiguration<String, String>().setName("cache1").setReadThrough(true).setCacheLoaderFactory(new Factory<CacheLoader<String, String>>() {
			@Override
			public CacheLoader<String, String> create() {
				return new CacheLoader<String, String>() {
					@Override
					public String load(String s) throws CacheLoaderException {
						return "loaded value";
					}

					@Override
					public Map<String, String> loadAll(Iterable<? extends String> iterable) throws CacheLoaderException {
						return null;
					}
				};
			}
		}));
		cache.put("a","a");
		cache.put("b","b");
		cache.put("c","c");
		cache1.put("a","a");
		cache1.put("b","b");
		cache1.put("c","c");
		cache1.get("g");
		 String c= cache1.getAndPut("d","vvvv");

		System.out.println(c);

	}

}
