package to.cwa.auth.config;

/**
 * The configuration for the auth system.  An instance is required to bootstrap the system.
 * This is usually instantiated from a json file or a database since it should not be persisted
 * to the source repository because of security.
 *
 * @author krico
 * @since 19/07/16.
 */
public interface AuthConfig {

    String getClientId();

    String getClientSecret();

    String getServiceAccountEmail();

    String getBrowserApiKey();

    String getDivId();
}
