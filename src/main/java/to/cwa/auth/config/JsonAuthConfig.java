package to.cwa.auth.config;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

/**
 * An immutable {@link AuthConfig} implementation that reads it's fields from a json file that looks like:
 * <p>
 * <pre>{ "{@value #ROOT_PROPERTY}" : { "clientId": "...", ...}}</pre>
 *
 * It looks for an object under the key {@value #ROOT_PROPERTY} and then for properties named with the same
 * names as the members of this class (e.g. "clientId", "clientSecret", etc)
 *
 * @author krico
 * @since 19/07/16.
 */
class JsonAuthConfig implements AuthConfig {
    private static final String ROOT_PROPERTY = "auth-user";

    private final String clientId;
    private final String clientSecret;
    private final String serviceAccountEmail;
    private final String browserApiKey;

    JsonAuthConfig(String data) throws IOException {
        this(new StringReader(data));
    }

    JsonAuthConfig(InputStream is) throws IOException {
        this(new InputStreamReader(is));
    }

    JsonAuthConfig(Reader reader) throws IOException {
        try {
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject root = new JSONObject(tokener);
            JSONObject configObject = root.getJSONObject(ROOT_PROPERTY);
            clientId = configObject.getString("clientId");
            clientSecret = configObject.getString("clientSecret");
            serviceAccountEmail = configObject.getString("serviceAccountEmail");
            browserApiKey = configObject.getString("browserApiKey");
        } catch (JSONException e) {
            throw new IOException("JSON parsing failed: " + e, e);
        }
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public String getServiceAccountEmail() {
        return serviceAccountEmail;
    }

    @Override
    public String getBrowserApiKey() {
        return browserApiKey;
    }
}
