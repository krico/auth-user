package to.cwa.auth.config;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;

import static junit.framework.TestCase.assertEquals;

/**
 * @author krico
 * @since 19/07/16.
 */
public class JsonAuthConfigTest {

    private static final String JSON_DATA = "{\"auth-user\":{\"clientId\":\"CID\",\"clientSecret\":\"secret\",\"serviceAccountEmail\":\"foo@bar.com\"}}";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseInvalidThrows() throws Exception {
        thrown.expect(IOException.class);
        thrown.expectMessage("begin with '{'");
        new JsonAuthConfig("");
    }

    @Test
    public void parseEmptyThrows() throws Exception {
        thrown.expect(IOException.class);
        thrown.expectMessage("auth-user");
        new JsonAuthConfig("{}");
    }

    @Test
    public void parseMissingThrows() throws Exception {
        thrown.expect(IOException.class);
        new JsonAuthConfig("{\"auth-user\":{}}");
    }

    private void assertConfig(AuthConfig config) {
        assertEquals("CID", config.getClientId());
        assertEquals("secret", config.getClientSecret());
        assertEquals("foo@bar.com", config.getServiceAccountEmail());
    }

    @Test
    public void parseGoodString() throws Exception {
        AuthConfig config = new JsonAuthConfig(JSON_DATA);
        assertConfig(config);
    }

    @Test
    public void parseGoodReader() throws Exception {
        AuthConfig config = new JsonAuthConfig(new StringReader(JSON_DATA));
        assertConfig(config);
    }

    @Test
    public void parseGoodInputStream() throws Exception {
        AuthConfig config = new JsonAuthConfig(new ByteArrayInputStream(JSON_DATA.getBytes()));
        assertConfig(config);
    }

}