package uk.co.idv.otp.app.spring;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.OutputFrame;
import uk.co.mruoc.fake.jwt.token.TokenRequest;
import uk.mruoc.fake.jwt.FakeJwtClient;

import java.time.Duration;
import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;

@Slf4j
public class LocalAuthServer extends GenericContainer<LocalAuthServer> {

    private static final int PORT = 80;

    public LocalAuthServer() {
        super("michaelruocco/fake-jwt-auth-server:latest");
        withEnv("ISSUER", "https://michaelruocco.eu.auth0.com/");
        withEnv("AUDIENCE", "https://idv-demo-api");
        withExposedPorts(PORT);
        withLogConsumer(this::logInfo);
    }

    public void waitForStartupToComplete() {
        await().pollDelay(Duration.ofSeconds(5))
                .pollInterval(Duration.ofMillis(500))
                .until(containerIsRunning());
    }

    public String getJwkSetEndpointUri() {
        return String.format("%s/.well-known/jwks.json", getEndpointUri());
    }

    public String loadAuthToken(String clientId) {
        FakeJwtClient jwtClient = new FakeJwtClient(getEndpointUri());
        TokenRequest request = TokenRequest.builder()
                .clientId(clientId)
                .build();
        return jwtClient.getToken(request).getParsedString();
    }

    private String getEndpointUri() {
        String ip = getContainerIpAddress();
        int port = getMappedPort(PORT);
        return String.format("http://%s:%s", ip, port);
    }

    private void logInfo(OutputFrame frame) {
        log.info(frame.getUtf8String());
    }

    private Callable<Boolean> containerIsRunning() {
        return () -> {
            boolean running = this.isRunning();
            log.info("checking aws container is running {}", running);
            return running;
        };
    }

}
