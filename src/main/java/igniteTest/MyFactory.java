package igniteTest;

import javax.cache.configuration.Factory;
import java.io.Serializable;

/**
 * Created by Razmik on 6/27/2016.
 */
public class MyFactory implements Factory<MyLoader>,Serializable{
    @Override
    public MyLoader create() {
        return new MyLoader();
    }
}
