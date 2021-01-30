package uk.co.idv.otp.app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.context.adapter.client.VerificationClient;
import uk.co.idv.otp.adapter.delivery.InMemoryDeliverOtp;
import uk.co.idv.otp.adapter.verificationloader.StubVerificationClient;
import uk.co.idv.otp.app.manual.Application;
import uk.co.idv.otp.app.manual.config.AppAdapter;
import uk.co.idv.otp.app.manual.config.DefaultAppAdapter;
import uk.co.idv.otp.config.OtpAppConfig;
import uk.co.idv.otp.config.VerificationLoaderConfig;
import uk.co.idv.otp.config.verificationloader.ContextVerificationLoaderConfig;
import uk.co.idv.otp.usecases.OtpVerificationRepository;
import uk.co.idv.otp.usecases.passcode.PasscodeGenerator;
import uk.co.idv.otp.usecases.send.DeliverOtp;

import java.time.Clock;

@Configuration
public class SpringCommonDomainConfig {

    @Bean
    public DeliverOtp deliverOtp(AppAdapter appAdapter) {
        return InMemoryDeliverOtp.builder()
                .clock(appAdapter.getClock())
                .idGenerator(appAdapter.getIdGenerator())
                .build();
    }

    @Bean
    public VerificationClient stubVerificationClient(AppAdapter appAdapter) {
        return new StubVerificationClient(appAdapter.getClock());
    }

    @Bean
    public VerificationLoaderConfig testFindIdentityConfig(VerificationClient client) {
        return new ContextVerificationLoaderConfig(client);
    }

    @Bean
    public AppAdapter appAdapter(Clock clock) {
        return DefaultAppAdapter.builder()
                .clock(clock)
                .build();
    }

    @Bean
    public OtpAppConfig appConfig(AppAdapter appAdapter,
                                  VerificationLoaderConfig verificationLoaderConfig,
                                  DeliverOtp deliverOtp,
                                  OtpVerificationRepository repository,
                                  PasscodeGenerator passcodeGenerator) {
        return OtpAppConfig.builder()
                .clock(appAdapter.getClock())
                .verificationLoader(verificationLoaderConfig.verificationLoader())
                .deliverOtp(deliverOtp)
                .repository(repository)
                .passcodeGenerator(passcodeGenerator)
                .build();
    }

    @Bean
    public Application application(OtpAppConfig appConfig) {
        return new Application(appConfig);
    }

}
