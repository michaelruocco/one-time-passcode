package uk.co.idv.otp.app.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.context.adapter.client.ContextClientConfig;
import uk.co.idv.context.adapter.client.RestContextClient;
import uk.co.idv.context.adapter.client.RestContextClientConfig;
import uk.co.idv.context.adapter.client.VerificationClient;
import uk.co.idv.otp.adapter.verificationloader.StubVerificationClient;
import uk.co.idv.otp.app.manual.config.AppAdapter;

@Slf4j
@Configuration
public class SpringVerificationClientConfig {

    @Profile("!test")
    @Bean
    public VerificationClient verificationClient(ObjectMapper mapper) {
        return RestContextClient.build(toContextClientConfig(mapper));
    }

    @Profile("test")
    @Bean
    public VerificationClient stubVerificationClient(AppAdapter appAdapter) {
        return new StubVerificationClient(appAdapter.getClock());
    }

    private static ContextClientConfig toContextClientConfig(ObjectMapper mapper) {
        return RestContextClientConfig.builder()
                .baseUri(contextUri())
                .mapper(mapper)
                .build();
    }

    private static String contextUri() {
        return System.getProperty("context.uri");
    }

}
