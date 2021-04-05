package uk.co.idv.otp.app.spring.config.security.fake;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import java.time.Clock;

@RequiredArgsConstructor
@Slf4j
public class FakeJwtDecoder implements JwtDecoder {

    private final FakeJwtValidator validator;
    private final FakeJwtFactory factory;

    public FakeJwtDecoder(Clock clock) {
        this(new FakeJwtValidator(), new FakeJwtFactory(clock));
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        log.warn("decoding token {} with fake jwt decoder", token);
        validator.validate(token);
        return factory.toJwt(token);
    }

}
