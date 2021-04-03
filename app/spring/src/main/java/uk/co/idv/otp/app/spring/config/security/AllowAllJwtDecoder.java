package uk.co.idv.otp.app.spring.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class AllowAllJwtDecoder implements JwtDecoder {

    private final Clock clock;

    @Override
    public Jwt decode(String token) throws JwtException {
        log.warn("allow all decoding token for value {}", token);
        Instant now = clock.instant();
        Instant expiresAt = now.plus(8, ChronoUnit.HOURS);
        Map<String, Object> headers = Map.of("allow-all-header", "allow-all-header");
        Map<String, Object> claims = Map.of("allow-all-claim", "allow-all-claim");
        return new Jwt(token, now, expiresAt, headers, claims);
    }

}
