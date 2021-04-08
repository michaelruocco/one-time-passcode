package uk.co.idv.otp.app.spring.config.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SubjectValidatorTest {

    private static final Collection<String> ALLOWED_SUBJECTS = Arrays.asList("subject-1", "subject-2");

    private final SubjectValidator validator = new SubjectValidator(ALLOWED_SUBJECTS);

    @Test
    void shouldReturnErrorIfJwtSubjectDoesNotMatchAllowedSubjectWithAmpersandClientsSuffix() {
        Jwt jwt = mock(Jwt.class);
        given(jwt.getSubject()).willReturn("other-subject@clients");
        given(jwt.getClaimAsString("azp")).willReturn("subject-1");

        OAuth2TokenValidatorResult result = validator.validate(jwt);

        OAuth2Error error = extractFirst(result.getErrors());
        assertThat(error.getErrorCode()).isEqualTo("invalid_token");
        assertThat(error.getDescription()).isEqualTo("Supported subject is missing");
        assertThat(error.getUri()).isNull();
    }

    @Test
    void shouldReturnErrorIfJwtAzpDoesNotMatchAllowedSubject() {
        Jwt jwt = mock(Jwt.class);
        given(jwt.getSubject()).willReturn("subject-1@clients");
        given(jwt.getClaimAsString("azp")).willReturn("other-subject");

        OAuth2TokenValidatorResult result = validator.validate(jwt);

        OAuth2Error error = extractFirst(result.getErrors());
        assertThat(error.getErrorCode()).isEqualTo("invalid_token");
        assertThat(error.getDescription()).isEqualTo("Supported azp is missing");
        assertThat(error.getUri()).isNull();
    }

    @Test
    void shouldReturnSuccessIfJwtSubjectAndAzpMatchesAllowedSubject() {
        Jwt jwt = mock(Jwt.class);
        given(jwt.getSubject()).willReturn("subject-1@clients");
        given(jwt.getClaimAsString("azp")).willReturn("subject-1");

        OAuth2TokenValidatorResult result = validator.validate(jwt);

        assertThat(result.getErrors()).isEmpty();
    }

    private static OAuth2Error extractFirst(Collection<OAuth2Error> errors) {
        return errors.stream().findFirst().orElseThrow(ErrorNotPresentException::new);
    }

    private static class ErrorNotPresentException extends RuntimeException {

        //intentionally blank

    }

}
