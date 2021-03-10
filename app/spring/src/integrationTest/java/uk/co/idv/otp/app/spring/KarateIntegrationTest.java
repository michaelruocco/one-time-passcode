package uk.co.idv.otp.app.spring;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import lombok.extern.slf4j.Slf4j;
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

    private static final String ENVIRONMENT = "idv-local";

    private static final int THREAD_COUNT = 4;

    @Container
    public static final LocalMongo MONGO = new LocalMongo();

    @Container
    public static final LocalAwsServices AWS_SERVICES = new LocalAwsServices();

    @BeforeAll
    static void setUp() {
        setUpAwsServices();
        setUpMongo();
        setUpApp();
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

    private static void setUpApp() {
        int port = findAvailableTcpPort();
        setApplicationProperties(port);
        startApp();
        waitForAppStartupToComplete(port);
    }

    private static void setApplicationProperties(int serverPort) {
        System.setProperty("environment", ENVIRONMENT);
        System.setProperty("server.port", Integer.toString(serverPort));
        System.setProperty("aws.accessKeyId", "abc");
        System.setProperty("aws.secretKey", "123");
        System.setProperty("aws.sns.endpoint.uri", AWS_SERVICES.getEndpointUri());
        System.setProperty("aws.ses.endpoint.uri", AWS_SERVICES.getEndpointUri());
        System.setProperty("spring.data.mongodb.uri", MONGO.getConnectionString());
        System.setProperty("response.filtering.enabled", "true");
        System.setProperty("spring.profiles.active", "simple-logging,test");
    }

}
