package uk.co.idv.otp.app.spring.config.security.fake;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.JwtValidationException;

import java.util.Collection;
import java.util.Collections;

public class FakeJwtValidator {

    public void validate(String token) {
        Collection<OAuth2Error> errors = toErrors(token);
        if (errors.isEmpty()) {
            return;
        }
        throw new JwtValidationException("invalid token", errors);
    }

    private Collection<OAuth2Error> toErrors(String token) {
        if (token.contains("invalid")) {
            return Collections.singleton(new OAuth2Error("invalid_token", "fake jwt decoder error", null));
        }
        return Collections.emptyList();
    }

}
