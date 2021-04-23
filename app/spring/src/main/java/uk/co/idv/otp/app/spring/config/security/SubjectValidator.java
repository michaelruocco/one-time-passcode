package uk.co.idv.otp.app.spring.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;

import static org.springframework.security.oauth2.core.OAuth2TokenValidatorResult.failure;

@RequiredArgsConstructor
public class SubjectValidator implements OAuth2TokenValidator<Jwt> {

    private static final String ERROR_CODE = "invalid_token";

    private final Collection<String> allowedSubjects;

    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        if (!isSubjectAllowed(jwt)) {
            return failure(toError("Supported subject is missing"));
        }
        if (!isAzpAllowed(jwt)) {
            return failure(toError("Supported azp is missing"));
        }
        return OAuth2TokenValidatorResult.success();
    }

    private boolean isSubjectAllowed(Jwt jwt) {
        return isSubjectAllowed(jwt.getSubject());
    }

    private boolean isSubjectAllowed(String subject) {
        return allowedSubjects.contains(subject.replace("@clients", ""));
    }

    private boolean isAzpAllowed(Jwt jwt) {
        return isAzpAllowed(jwt.getClaimAsString("azp"));
    }

    private boolean isAzpAllowed(String azp) {
        return allowedSubjects.contains(azp);
    }

    private static OAuth2Error toError(String message) {
        return new OAuth2Error(ERROR_CODE, message, null);
    }

}
