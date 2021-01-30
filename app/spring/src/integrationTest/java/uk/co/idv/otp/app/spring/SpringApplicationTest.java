package uk.co.idv.otp.app.spring;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;

import static org.assertj.core.api.Assertions.assertThatCode;

class SpringApplicationTest {

    @Test
    @SetSystemProperty(key = "spring.profiles.active", value = "stubbed,simple-logging")
    @SetSystemProperty(key = "server.port", value = "0")
    @SetSystemProperty(key = "response.filtering.enabled", value = "false")
    void applicationShouldStartWithStubbedAndSimpleLoggingProfiles() {
        assertThatCode(() -> SpringApplication.main(new String[0])).doesNotThrowAnyException();
    }

    @Test
    @SetSystemProperty(key = "spring.profiles.active", value = "stubbed")
    @SetSystemProperty(key = "server.port", value = "0")
    @SetSystemProperty(key = "response.filtering.enabled", value = "false")
    void applicationShouldStartWithStubbedProfile() {
        assertThatCode(() -> SpringApplication.main(new String[0])).doesNotThrowAnyException();
    }

}
