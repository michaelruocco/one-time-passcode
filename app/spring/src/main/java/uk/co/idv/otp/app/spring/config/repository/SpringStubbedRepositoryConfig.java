package uk.co.idv.otp.app.spring.config.repository;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.otp.config.repository.InMemoryRepositoryConfig;
import uk.co.idv.otp.usecases.OtpVerificationRepository;

@Configuration
@EnableAutoConfiguration(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
})
@Profile("stubbed")
public class SpringStubbedRepositoryConfig {

    @Bean
    public OtpVerificationRepository verificationRepository() {
        return new InMemoryRepositoryConfig().verificationRepository();
    }

}
