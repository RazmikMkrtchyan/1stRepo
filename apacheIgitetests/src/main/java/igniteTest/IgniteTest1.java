package igniteTest;

/**
 * Created by Razmik.Mkrtchyan on 5/31/2016.
 */
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMemoryMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.cache.Cache;
import java.util.function.Function;
import java.util.function.Supplier;

public class IgniteTest1 {


        public static void main(String[] args) {
            String igniteConfigPath = "ignite-config.xml";
            ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("ignite-config.xml");
            Ignite ignite1=null;
              try(Ignite ignite = Ignition.start(igniteConfigPath);   Ignite ignite01= Ignition.start()) {
                  IgniteCache<Integer, String> cache = ignite.getOrCreateCache("testCache");

   //simple put and get
                  scenario1(cache);
// using EntryProcessor  for updating several fields
                  scenario2(cache);

                  //test3 scan queries
                  CacheConfiguration<Integer, Person> configuration = new CacheConfiguration<Integer, Person>().setCacheMode(CacheMode.REPLICATED ).setMemoryMode(CacheMemoryMode.OFFHEAP_TIERED).setName("testCacheName1");
                  IgniteCache<Integer, Person> cache3 = ignite.getOrCreateCache(configuration);
                  cache3.put(1, new Person(9000));
                  cache3.put(2, new Person(1000));
                  cache3.put(3, new Person(500));
                  cache3.put(4, new Person(8000));
      //queries but not sql
              scenario3(cache3);

            IgniteCache<Integer, Person> cache5 = ignite01.getOrCreateCache("testCacheName1");
            cache5.put(6,new Person(97000));

                          cache5.invoke(6, (entry, arg) -> {
                              Person val = entry.getValue();
                                val.setSalary(111111);
                              entry.setValue(val);

                              return null;
                          });
                  cache5.put(8,new Person(97000));

                  //transactions
                  IgniteTransactions transactions = ignite.transactions();
                  try (Transaction tx = transactions.txStart()) {
                      cache5.invoke(6, (entry, arg) -> {
                          Person val = entry.getValue();
                          val.setSalary(222);
                          entry.setValue(val);
                          return null;
                      });
                      tx.commit();
                  }
                  try (Transaction tx = transactions.txStart()) {
                      cache5.invoke(6, (entry, arg) -> {
                          Person val = entry.getValue();
                          val.setSalary(2252);
                          entry.setValue(val);
                        return null;
                      });
                      tx.commit();
                  }
                }


        }



 private static void scenario1(IgniteCache<Integer, String> cache){
    //test1- puts and gets in cache
    Supplier<String> supplier = () -> {
        for (int i = 0; i < 10; i++)
            cache.put(i, Integer.toString(i));

        for (int i = 0; i < 10; i++)
            System.out.println("Got [key=" + i + ", val=" + cache.get(i) + ']');

        return "";
    };
    IgniteTest.durationOfFunctionByMilliseconds(supplier);
}
    private static void scenario2(IgniteCache<Integer, String> cache){
        // test2-taking  cached value and incrementing it by 1.(update,with not full state)
        Supplier<String> supplier1 = () -> {
            for (int i = 0; i < 10; i++)
                cache.invoke(i, (entry, arg) -> {
                    String val = entry.getValue();

                    entry.setValue(val == null ? "1" : "val" + 1);

                    return null;
                });
            return "";
        };
        IgniteTest.durationOfFunctionByMilliseconds(supplier1);
    }

    private static void scenario3(IgniteCache<Integer, Person> cache3) {
        // Find only persons earning more than 1,000.
        try (QueryCursor<Cache.Entry<Integer, Person>> cursor = cache3.query(new ScanQuery<>((k, p) -> p.getSalary() > 1000))) {
            for (Cache.Entry<Integer, Person> p : cursor)
                System.out.println(p.getValue().getSalary());
        }
    }
}

