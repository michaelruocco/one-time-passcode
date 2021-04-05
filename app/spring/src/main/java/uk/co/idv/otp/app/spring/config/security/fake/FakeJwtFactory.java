package uk.co.idv.otp.app.spring.config.security.fake;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@RequiredArgsConstructor
public class FakeJwtFactory {

    private static final Map<String, Object> HEADERS = Map.of("fake-decoder-header", "fake-decoder-header-value");
    private static final Map<String, Object> CLAIMS = Map.of("fake-decoder-claim", "fake-decoder-claim-value");

    private final Clock clock;

    public Jwt toJwt(String token) {
        Instant now = clock.instant();
        return Jwt.withTokenValue(token)
                .issuedAt(now)
                .expiresAt(now.plus(8, ChronoUnit.HOURS))
                .headers(h -> h.putAll(HEADERS))
                .claims(c -> c.putAll(CLAIMS))
                .build();
    }

}
