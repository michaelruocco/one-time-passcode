package uk.co.idv.otp.app.spring.config.security.fake;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@RequiredArgsConstructor
public class FakeJwtFactory {

    private final Clock clock;

    public Jwt toJwt(String token) {
        Instant now = clock.instant();
        Instant expiresAt = now.plus(8, ChronoUnit.HOURS);
        Map<String, Object> headers = Map.of("fake-decoder-header", "fake-decoder-header-value");
        Map<String, Object> claims = Map.of("fake-decoder-claim", "fake-decoder-claim-value");
        return new Jwt(token, now, expiresAt, headers, claims);
    }

}
