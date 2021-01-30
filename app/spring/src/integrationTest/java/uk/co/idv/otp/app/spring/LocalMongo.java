package uk.co.idv.otp.app.spring;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.OutputFrame;

import java.time.Duration;
import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;
import static org.testcontainers.utility.MountableFile.forHostPath;

@Slf4j
public class LocalMongo extends GenericContainer<LocalMongo> {

    private static final String USERNAME = "idv";
    private static final String PASSWORD = "welcome01";
    private static final String DATABASE = "idv-local";

    private static final int PORT = 27017;

    public LocalMongo() {
        super("mongo");
        withEnv("MONGO_INITDB_ROOT_USERNAME", USERNAME);
        withEnv("MONGO_INITDB_ROOT_PASSWORD", PASSWORD);
        withEnv("MONGO_INITDB_DATABASE", DATABASE);
        withCopyFileToContainer(forHostPath("mongo/mongo-init.js"), "/docker-entrypoint-initdb.d/mongo-init.js");
        withExposedPorts(PORT);
        withLogConsumer(this::logInfo);
    }

    public void waitForStartupToComplete() {
        await().pollDelay(Duration.ofSeconds(5))
                .pollInterval(Duration.ofMillis(500))
                .until(containerIsRunning());
    }

    public String getConnectionString() {
        String ip = getContainerIpAddress();
        int port = getMappedPort(PORT);
        return String.format("mongodb://%s:%s@%s:%d/%s", USERNAME, PASSWORD, ip, port, DATABASE);
    }

    private void logInfo(OutputFrame frame) {
        log.info(frame.getUtf8String());
    }

    private Callable<Boolean> containerIsRunning() {
        return () -> {
            boolean running = this.isRunning();
            log.info("checking mongo container is running {}", running);
            return running;
        };
    }

}
