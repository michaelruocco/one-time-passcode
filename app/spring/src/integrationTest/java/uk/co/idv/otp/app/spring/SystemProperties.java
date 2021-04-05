package uk.co.idv.otp.app.spring;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
@Slf4j
class SystemProperties {

    private static final String SERVER_PORT = "server.port";
    private static final String AUTH_TOKEN = "auth.token";
    private static final String ENVIRONMENT = "environment";

    private static final String AWS_SNS_ENDPOINT_URI = "aws.sns.endpoint.uri";
    private static final String AWS_SES_ENDPOINT_URI = "aws.ses.endpoint.uri";

    private static final String MONGO_URI = "spring.data.mongodb.uri";
    private static final String JWK_SET_OVERRIDE_URI = "spring.security.oauth2.resourceserver.jwk-set-uri";

    private static final String AWS_ACCESS_KEY_ID = "aws.accessKeyId";
    private static final String AWS_SECRET_KEY = "aws.secretKey";
    private static final String REQUEST_LOGGING_ENABLED = "request.logging.enabled";
    private static final String RESPONSE_LOGGING_ENABLED = "response.logging.enabled";
    private static final String RESPONSE_FILTERING_ENABLED = "response.filtering.enabled";
    private static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

    public static void setServerPort(int serverPort) {
        System.setProperty(SERVER_PORT, Integer.toString(serverPort));
    }

    public static void setAuthToken(String authToken) {
        System.setProperty(AUTH_TOKEN, authToken);
    }

    public static void setAwsServiceEndpointUri(String awsServiceEndpointUri) {
        System.setProperty(AWS_SNS_ENDPOINT_URI, awsServiceEndpointUri);
        System.setProperty(AWS_SES_ENDPOINT_URI, awsServiceEndpointUri);
    }

    public static void setMongoUri(String mongoUri) {
        System.setProperty(MONGO_URI, mongoUri);
    }

    public static void setJwkSetOverrideUri(String jwkSetOverrideUri) {
        System.setProperty(JWK_SET_OVERRIDE_URI, jwkSetOverrideUri);
    }

    public static void setDefaultProperties() {
        System.setProperty(ENVIRONMENT, "idv-local");
        System.setProperty(AWS_ACCESS_KEY_ID, "abc");
        System.setProperty(AWS_SECRET_KEY, "123");
        System.setProperty(REQUEST_LOGGING_ENABLED, "true");
        System.setProperty(RESPONSE_LOGGING_ENABLED, "true");
        System.setProperty(RESPONSE_FILTERING_ENABLED, "true");
        System.setProperty(SPRING_PROFILES_ACTIVE, "simple-logging,test");
    }

    public static void clear() {
        System.clearProperty(SERVER_PORT);
        System.clearProperty(AUTH_TOKEN);
        System.clearProperty(AWS_SNS_ENDPOINT_URI);
        System.clearProperty(AWS_SES_ENDPOINT_URI);
        System.clearProperty(MONGO_URI);
        System.clearProperty(JWK_SET_OVERRIDE_URI);
        System.clearProperty(ENVIRONMENT);
        System.clearProperty(AWS_ACCESS_KEY_ID);
        System.clearProperty(AWS_SECRET_KEY);
        System.clearProperty(REQUEST_LOGGING_ENABLED);
        System.clearProperty(RESPONSE_LOGGING_ENABLED);
        System.clearProperty(RESPONSE_FILTERING_ENABLED);
        System.clearProperty(SPRING_PROFILES_ACTIVE);
    }

}
