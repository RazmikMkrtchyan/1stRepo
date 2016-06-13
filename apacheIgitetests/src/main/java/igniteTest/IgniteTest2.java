package igniteTest;

/**
 * Created by Razmik.Mkrtchyan on 5/31/2016.
 */
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cache.query.TextQuery;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.cache.Cache;
import java.util.function.Supplier;

public class IgniteTest2 {


        public static void main(String[] args) {
            String igniteConfigPath = "ignite-config.xml";
            ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("ignite-config.xml");
            Ignite ignite1=null;
                try (Ignite ignite = Ignition.start(igniteConfigPath)) {
                    IgniteCache<Integer, String> cache = ignite.getOrCreateCache("testCache");
                    //test3 scan queries
                    IgniteCache<Integer, Person> cache3 = ignite.getOrCreateCache("sys_cache_conf1");
                    cache3.put(1,new Person(9000));
                    cache3.put(2,new Person(1000));
                    cache3.put(3,new Person(500));
                    cache3.put(4,new Person(8000));

            // Find only persons earning more than 1,000.
                    try (QueryCursor<Cache.Entry<Integer, Person>> cursor = cache3.query(new ScanQuery<>((k, p) -> p.getSalary() > 1000))) {
                        for (Cache.Entry<Integer, Person> p : cursor)
                            System.out.println(p.getValue().getSalary());
                    }

                    TextQuery txt = new TextQuery(Person.class, "Master Degree");

                    try (QueryCursor<Cache.Entry<Integer, Person>> masters = cache.query(txt)) {
                        for (Cache.Entry<Integer, Person> e : masters)
                            System.out.println(e.getValue().toString());
                    }

                }
                }



        }

