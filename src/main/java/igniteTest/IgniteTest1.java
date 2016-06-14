package igniteTest;


import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCompute;

import org.apache.ignite.lang.IgniteRunnable;
/**
 * Created by Razmik.Mkrtchyan on 5/30/2016.
 */
public class IgniteTest1 {

	public static void main(String[] args) {
               Ignite ignite=Ignition.start("ignite-config.xml");
		IgniteCache<Integer,Integer> cache= ignite.getOrCreateCache("test1");
		cache.put(1,1);
	
	}

}
