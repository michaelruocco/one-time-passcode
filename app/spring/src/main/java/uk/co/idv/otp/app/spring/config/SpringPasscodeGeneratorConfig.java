package uk.co.idv.otp.app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.otp.adapter.passcode.generator.IncrementingPasscodeGenerator;
import uk.co.idv.otp.adapter.passcode.generator.RandomPasscodeGenerator;
import uk.co.idv.otp.app.manual.config.AppAdapter;
import uk.co.idv.otp.usecases.passcode.PasscodeGenerator;

@Configuration
public class SpringPasscodeGeneratorConfig {

    @Profile("!test")
    @Bean
    public PasscodeGenerator randomPasscodeGenerator(AppAdapter appAdapter) {
        return new RandomPasscodeGenerator(appAdapter.getClock());
    }

    @Profile("test")
    @Bean
    public PasscodeGenerator nonRandomPasscodeGenerator(AppAdapter appAdapter) {
        return new IncrementingPasscodeGenerator(appAdapter.getClock());
    }

}
