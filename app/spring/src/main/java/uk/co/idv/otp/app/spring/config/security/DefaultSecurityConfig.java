package uk.co.idv.otp.app.spring.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.time.Clock;

@Configuration
@EnableWebSecurity
@Slf4j
public class DefaultSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${auth0.audience}")
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    @Value("${spring.security.oauth2.resourceserver.jwt.jwks-override-uri:}")
    private String jwksOverrideUri;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer().jwt();
        http.authorizeRequests()
                .mvcMatchers("/v1/actuator").permitAll()
                .mvcMatchers("/v1/otp-verifications").permitAll()
                .and().cors()
                .and().oauth2ResourceServer().jwt();
    }

    @Bean
    @ConditionalOnProperty(value = "spring.security.oauth2.resourceserver.jwt.jwks-override-uri")
    public JwtDecoder overrideJwtDecoder() {
        log.warn("jwt decoder overridden using jwks override url {}, this should only be enabled for local demo or testing purposes", jwksOverrideUri);
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwksOverrideUri).build();
        jwtDecoder.setJwtValidator(tokenValidator());
        return jwtDecoder;
    }

    @Bean
    @Profile("insecure")
    public JwtDecoder stubbedJwtDecoder(Clock clock) {
        log.warn("allow all jwt decoder enabled, this should only be enabled for local demo or testing purposes");
        return new AllowAllJwtDecoder(clock);
    }

    @Bean
    @ConditionalOnMissingBean(value = JwtDecoder.class)
    public JwtDecoder defaultJwtDecoder() {
        log.info("default jwt decoder enabled using issuer {}", issuer);
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(issuer);
        jwtDecoder.setJwtValidator(tokenValidator());
        return jwtDecoder;
    }

    private OAuth2TokenValidator<Jwt> tokenValidator() {
        OAuth2TokenValidator<Jwt> withAudience = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        return new DelegatingOAuth2TokenValidator<>(withIssuer, withAudience);
    }

}
