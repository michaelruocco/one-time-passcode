package uk.co.idv.otp.app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.otp.adapter.delivery.InMemoryDeliverOtp;
import uk.co.idv.otp.app.manual.config.AppAdapter;
import uk.co.idv.otp.config.delivery.SnsDeliveryConfig;
import uk.co.idv.otp.usecases.send.DeliverOtp;

@Configuration
public class SpringDeliverOtpConfig {

    @Profile("!stubbed")
    @Bean
    public DeliverOtp deliverOtp(AppAdapter appAdapter) {
        return SnsDeliveryConfig.builder()
                .clock(appAdapter.getClock())
                .endpointUri(loadSnsEndpointUri())
                .region(loadAwsRegion())
                .build()
                .deliverOtp();
    }

    @Profile("stubbed")
    @Bean
    public DeliverOtp inMemoryDeliverOtp(AppAdapter appAdapter) {
        return InMemoryDeliverOtp.builder()
                .clock(appAdapter.getClock())
                .idGenerator(appAdapter.getIdGenerator())
                .build();
    }

    private static String loadAwsRegion() {
        return System.getProperty("aws.region", "eu-west-1");
    }

    private static String loadSnsEndpointUri() {
        return System.getProperty("aws.sns.endpoint.uri");
    }

}
