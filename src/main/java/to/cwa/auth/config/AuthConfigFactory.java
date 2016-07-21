package to.cwa.auth.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * This class knows how to create an {@link AuthConfig} from different data sources.
 *
 * @author krico
 * @since 19/07/16.
 */
public class AuthConfigFactory {

    /**
     * Instantiate a new AuthConfig from a json url resource
     *
     * @param url pointing to the resource
     * @return an AuthConfig
     * @throws IOException if there are problems reading or parsing the resource
     */
    public AuthConfig newFromJson(URL url) throws IOException {
        try (InputStream is = url.openStream()) {
            return new JsonAuthConfig(is);
        }
    }

    public AuthConfig newFromJson(File path) throws IOException {
        return newFromJson(path.toURI().toURL());
    }
}
