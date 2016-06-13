package igniteTest;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.affinity.AffinityKeyMapped;
import org.apache.ignite.lang.IgniteCallable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Razmik.Mkrtchyan on 6/7/2016.
 */
public class IgniteCompute {

    public static void main(String[] args) {
        try (Ignite ignite = Ignition.start()) {
            Collection<IgniteCallable<Integer>> calls = new ArrayList<>();

            // Iterate through all the words in the sentence and create Callable jobs.
            for (final String word : "Count characters using callable".split(" "))
                calls.add(word::length);

            // Execute collection of Callables on the grid.
            Collection<Integer> res = ignite.compute().call(calls);

            // Add up all the results.
            int sum = res.stream().mapToInt(Integer::intValue).sum();

            System.out.println("Total number of characters is '" + sum + "'.");
            IgniteCache<PersonKey, Person> personsCache = ignite.getOrCreateCache("myCache");
            IgniteCache<String, String> companiesCache = ignite.getOrCreateCache("myCache1");

// Instantiate person keys with the same company ID which is used as affinity key.
            PersonKey personKey1 = new PersonKey("myPersonId1", "myCompanyId");
            PersonKey personKey2 = new PersonKey("myPersonId2", "myCompanyId");

            Person p1 = new Person(personKey1);
            Person p2 = new Person(personKey2);

// Both, the company and the person objects will be cached on the same node.
            companiesCache.put("myCompanyId", "companyNew");

            personsCache.put(personKey1, p1);
            personsCache.put(personKey2, p2);




            String companyId = "myCompanyId";

// Execute Runnable on the node where the key is cached.
            ignite.compute().affinityRun("myCache", companyId, () -> {
                String company = companiesCache.get(companyId);

                // Since we collocated persons with the company in the above example,
                // access to the persons objects is local.
                Person person1 = personsCache.get(personKey1);
                Person person2 = personsCache.get(personKey2);
            });
            ignite.getOrCreateCache("myCache3");
        }

    }

}

