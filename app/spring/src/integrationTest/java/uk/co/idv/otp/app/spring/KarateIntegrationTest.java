package uk.co.idv.otp.app.spring;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.SocketUtils.findAvailableTcpPort;
import static uk.co.idv.otp.app.spring.AppRunner.startApp;
import static uk.co.idv.otp.app.spring.AppRunner.waitForAppStartupToComplete;

@Testcontainers
@Slf4j
class KarateIntegrationTest {

    private static final int THREAD_COUNT = 4;

    @Container
    public static final LocalMongo MONGO = new LocalMongo();

    @Container
    public static final LocalAwsServices AWS_SERVICES = new LocalAwsServices();

    @Container
    public static final LocalAuthServer AUTH_SERVER = new LocalAuthServer();

    @BeforeAll
    static void setUp() {
        setUpAwsServices();
        setUpMongo();
        setUpAuthServer();
        setUpApp();
    }

    @AfterAll
    static void tearDown() {
        SystemProperties.clear();
    }

    @Test
    void runParallelFeatures() {
        String reportDir = "build/reports/karate";
        Results results = Runner.path(getFeaturePaths())
                .reportDir(reportDir)
                .parallel(THREAD_COUNT);

        assertThat(results.getFailCount())
                .withFailMessage(results.getErrorMessages())
                .isZero();
    }

    private static String[] getFeaturePaths() {
        return new String[]{
                "classpath:actuator",
                "classpath:otp"
        };
    }

    private static void setUpAwsServices() {
        AWS_SERVICES.waitForStartupToComplete();
    }

    private static void setUpMongo() {
        MONGO.waitForStartupToComplete();
    }

    private static void setUpAuthServer() {
        AUTH_SERVER.waitForStartupToComplete();
    }

    private static void setUpApp() {
        int port = findAvailableTcpPort();
        setSystemProperties(port);
        startApp();
        waitForAppStartupToComplete(port);
    }

    private static void setSystemProperties(int serverPort) {
        SystemProperties.setServerPort(serverPort);
        SystemProperties.setAuthToken(AUTH_SERVER.loadAuthToken("test-client-id"));
        SystemProperties.setAwsServiceEndpointUri(AWS_SERVICES.getEndpointUri());
        SystemProperties.setMongoUri(MONGO.getConnectionString());
        SystemProperties.setJwkSetUri(AUTH_SERVER.getJwkSetEndpointUri());
        SystemProperties.setDefaultProperties();
    }

}
