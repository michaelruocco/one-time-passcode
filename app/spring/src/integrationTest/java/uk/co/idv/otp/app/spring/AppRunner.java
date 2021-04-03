package uk.co.idv.otp.app.spring;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

public class AppRunner {

    public static void startApp() {
        try {
            SpringApplication.main(new String[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void waitForAppStartupToComplete(int port) {
        await().dontCatchUncaughtExceptions()
                .atMost(Duration.ofSeconds(5))
                .pollInterval(Duration.ofMillis(250))
                .until(PortReady.local(port));
    }

}
