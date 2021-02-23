package uk.co.idv.otp.app.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.context.adapter.verification.client.RestVerificationClient;
import uk.co.idv.context.adapter.verification.client.RestVerificationClientConfig;
import uk.co.idv.context.adapter.verification.client.VerificationClient;
import uk.co.idv.context.adapter.verification.client.VerificationClientConfig;
import uk.co.idv.otp.adapter.verification.StubVerificationClient;
import uk.co.idv.otp.app.plain.config.AppAdapter;

@Slf4j
@Configuration
public class SpringVerificationClientConfig {

    @Profile("!test")
    @Bean
    public VerificationClient verificationClient(ObjectMapper mapper) {
        return RestVerificationClient.build(toContextClientConfig(mapper));
    }

    @Profile("test")
    @Bean
    public VerificationClient stubVerificationClient(AppAdapter appAdapter) {
        return new StubVerificationClient(appAdapter.getClock());
    }

    private static VerificationClientConfig toContextClientConfig(ObjectMapper mapper) {
        return RestVerificationClientConfig.builder()
                .baseUri(contextUri())
                .mapper(mapper)
                .build();
    }

    private static String contextUri() {
        return System.getProperty("context.uri");
    }

}
