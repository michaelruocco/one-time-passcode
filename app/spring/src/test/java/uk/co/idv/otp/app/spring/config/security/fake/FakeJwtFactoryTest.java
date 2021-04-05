package uk.co.idv.otp.app.spring.config.security.fake;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class FakeJwtFactoryTest {

    private static final Instant NOW = Instant.now();
    private static final Clock CLOCK = Clock.fixed(NOW, ZoneId.systemDefault());

    private final FakeJwtFactory factory = new FakeJwtFactory(CLOCK);

    @Test
    void shouldContainOneClaim() {
        String token = "any-token";

        Jwt jwt = factory.toJwt(token);

        assertThat(jwt.getClaims()).hasSize(1);
    }

    @Test
    void shouldReturnTokenIssuedAtCurrentTime() {
        String token = "any-token";

        Jwt jwt = factory.toJwt(token);

        assertThat(jwt.getIssuedAt()).isEqualTo(NOW);
    }

    @Test
    void shouldReturnTokenExpiresAtCurrentTimePlusEightHours() {
        String token = "any-token";

        Jwt jwt = factory.toJwt(token);

        assertThat(jwt.getExpiresAt()).isEqualTo(NOW.plus(8, ChronoUnit.HOURS));
    }

    @Test
    void shouldReturnClaim() {
        String token = "any-token";

        Jwt jwt = factory.toJwt(token);

        assertThat(jwt.getClaims()).containsEntry(
                "fake-decoder-claim", "fake-decoder-claim-value"
        );
    }

    @Test
    void shouldReturnHeaders() {
        String token = "any-token";

        Jwt jwt = factory.toJwt(token);

        assertThat(jwt.getHeaders()).containsExactly(
                entry("fake-decoder-header", "fake-decoder-header-value")
        );
    }

}
