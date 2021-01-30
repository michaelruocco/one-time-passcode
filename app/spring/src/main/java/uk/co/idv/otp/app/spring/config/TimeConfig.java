package uk.co.idv.otp.app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.mruoc.test.clock.OffsetClock;

import java.time.Clock;

@Configuration
public class TimeConfig {

    @Profile("!test")
    @Bean
    public Clock defaultClock() {
        return Clock.systemUTC();
    }

    @Profile("test")
    @Bean
    public Clock testClock() {
        return new OffsetClock();
    }

}
