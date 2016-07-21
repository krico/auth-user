package to.cwa.auth;

import com.google.identitytoolkit.GitkitClient;
import to.cwa.auth.config.AuthConfig;

import java.io.FileInputStream;

/**
 * @author krico
 * @since 19/07/16.
 */
public class Authenticate {
    private final AuthConfig config;

    public Authenticate(AuthConfig config) {
        this.config = config;
    }

    public void test(){
        GitkitClient gitkitClient = GitkitClient.newBuilder()
                .setGoogleClientId(config.getClientId())
                .setServiceAccountEmail(config.getServiceAccountEmail())
               // .setKeyStream(new FileInputStream("path-to-your-service-account-private-file"))
                .setWidgetUrl("/gitkit.jsp")
                .setCookieName("gtoken")
                .build();

    }
}
