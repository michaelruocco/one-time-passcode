package uk.co.idv.otp.app.spring.config.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class AudienceValidatorTest {

    private static final String AUDIENCE = "TEST_AUDIENCE";

    private final AudienceValidator validator = new AudienceValidator(AUDIENCE);

    @Test
    void shouldReturnResultWithNoErrorsIfAudienceIsValid() {
        Jwt jwt = buildJwtWithAudience(AUDIENCE);

        OAuth2TokenValidatorResult result = validator.validate(jwt);

        assertThat(result.getErrors()).isEmpty();
    }

    @Test
    void shouldReturnResultWithErrorsIfAudienceIsInvalid() {
        Jwt jwt = buildJwtWithAudience("other-audience");

        OAuth2TokenValidatorResult result = validator.validate(jwt);

        assertThat(result.getErrors())
                .map(OAuth2Error::toString)
                .containsExactly("[invalid_token] The required audience is missing");
    }

    private Jwt buildJwtWithAudience(String audience) {
        return Jwt.withTokenValue("my-token")
                .headers(h -> h.put("dummy", "dummy"))
                .audience(Collections.singleton(audience))
                .build();
    }
}
