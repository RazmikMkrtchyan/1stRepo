package igniteTest;

import org.apache.ignite.cache.store.CacheStoreAdapter;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Razmik on 6/27/2016.
 */
public class MyLoader extends CacheStoreAdapter<Integer,String> implements Serializable {
    private CustomObj customObj;

    public MyLoader(CustomObj customObj) {
        this.customObj = customObj;
    }

    @Override
    public String load(Integer key) throws CacheLoaderException {
        System.out.println(customObj.getClass());
        return "ssss";
    }

    @Override
    public Map<Integer, String> loadAll(Iterable<? extends Integer> keys) throws CacheLoaderException {
        return new HashMap<>();
    }

    @Override
    public void write(Cache.Entry<? extends Integer, ? extends String> entry) throws CacheWriterException {

    }

    @Override
    public void delete(Object key) throws CacheWriterException {

    }
}
