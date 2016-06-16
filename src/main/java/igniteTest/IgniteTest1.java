package igniteTest;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

import org.apache.ignite.IgniteCache;

/**
 * Created by Razmik.Mkrtchyan on 5/30/2016.
 */
public class IgniteTest1 {

	public static void main(String[] args) {
		Ignite ignite=Ignition.start("ignite-config.xml");
		IgniteCache<Integer,Integer> cache= ignite.getOrCreateCache("test1");
		cache.put(2,2);
		IgniteCache<Integer,Integer> cache1= ignite.getOrCreateCache("test2");
		cache1.put(1,1);

	}

}
