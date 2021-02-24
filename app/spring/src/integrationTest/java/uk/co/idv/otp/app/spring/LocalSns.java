package uk.co.idv.otp.app.spring;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.OutputFrame;

import java.time.Duration;
import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;

@Slf4j
public class LocalSns extends GenericContainer<LocalSns> {

    private static final int PORT = 4566;

    public LocalSns() {
        super("localstack/localstack:latest");
        withEnv("SERVICES", "sns");
        withExposedPorts(PORT);
        withLogConsumer(this::logInfo);
    }

    public void waitForStartupToComplete() {
        await().pollDelay(Duration.ofSeconds(5))
                .pollInterval(Duration.ofMillis(500))
                .until(containerIsRunning());
    }

    public String getEndpointUri() {
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
            log.info("checking sns container is running {}", running);
            return running;
        };
    }

}
