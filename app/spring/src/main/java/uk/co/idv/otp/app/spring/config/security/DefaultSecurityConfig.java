package uk.co.idv.otp.app.spring.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import uk.co.idv.otp.app.spring.config.security.fake.FakeJwtDecoder;

import java.time.Clock;
import java.util.Collection;

@Configuration
@Slf4j
public class DefaultSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.oauth2.resourceserver.jwt.audience}")
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer}")
    private String issuer;

    @Value("${spring.security.oauth2.resourceserver.jwt.subjects}")
    private Collection<String> subjects;

    @Value("${spring.security.oauth2.resourceserver.jwk-set-uri}")
    private String jwkSetUri;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(auth -> auth
                .mvcMatchers("/actuator/**").permitAll()
                .mvcMatchers("/v1/**").authenticated()
        ).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

    @Bean
    @Profile("stubbed")
    public JwtDecoder stubbedJwtDecoder(Clock clock) {
        log.warn("using fake jwt decoder, this should only be enabled for local demo or testing purposes");
        return new FakeJwtDecoder(clock);
    }

    @Bean
    @Profile("!stubbed")
    public JwtDecoder jwtDecoder() {
        log.warn("using jwt decoder with jwk set uri {}", jwkSetUri);
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
        jwtDecoder.setJwtValidator(tokenValidator());
        return jwtDecoder;
    }

    private OAuth2TokenValidator<Jwt> tokenValidator() {
        log.info("setting up token validator for subjects {}", subjects);
        OAuth2TokenValidator<Jwt> withSubjects = new SubjectValidator(subjects);
        log.info("setting up token validator for audience {}", audience);
        OAuth2TokenValidator<Jwt> withAudience = new AudienceValidator(audience);
        log.info("setting up token validator for issuer {}", issuer);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        return new DelegatingOAuth2TokenValidator<>(withIssuer, withAudience, withSubjects);
    }

}
