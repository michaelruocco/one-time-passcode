package uk.co.idv.otp.app.spring;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

public class AppRunner {

    public static void startApp() {
        SpringApplication.main(new String[0]);
    }

    public static void waitForAppStartupToComplete(int port) {
        await().dontCatchUncaughtExceptions()
                .atMost(Duration.ofSeconds(5))
                .pollInterval(Duration.ofMillis(250))
                .until(PortReady.local(port));
    }

}
