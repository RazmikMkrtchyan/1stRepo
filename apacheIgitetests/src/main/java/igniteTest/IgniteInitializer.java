package igniteTest;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Razmik.Mkrtchyan on 5/31/2016.
 */
public class IgniteInitializer {
    public URI getIgniteConfigurationUri() throws URISyntaxException {
        return new File("src/main/resources/ignite-config.xml").toURI();
    }
}
