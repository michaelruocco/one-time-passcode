package uk.co.idv.otp.app.spring.config.security.fake;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.JwtValidationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class FakeJwtValidatorTest {

    private final FakeJwtValidator validator = new FakeJwtValidator();

    @Test
    void shouldDoNothingIfTokenDoesNotContainStringInvalid() {
        String token = "my-token";

        assertThatCode(() -> validator.validate(token)).doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionIfTokenContainsStringInvalid() {
        String token = "my-invalid-token";

        JwtValidationException exception = catchThrowableOfType(
                () -> validator.validate(token),
                JwtValidationException.class
        );

        assertThat(exception).hasMessage("invalid token");
        assertThat(exception.getErrors()).hasSize(1);
    }

}
