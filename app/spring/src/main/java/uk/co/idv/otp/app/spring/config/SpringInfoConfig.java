package uk.co.idv.otp.app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.otp.app.spring.config.properties.SecureProperties;
import uk.co.idv.otp.app.spring.info.SystemPropertyInfoContributor;

@Configuration
public class SpringInfoConfig {

    @Bean
    public SystemPropertyInfoContributor profileContributor() {
        return new SystemPropertyInfoContributor(new SecureProperties());
    }

}
