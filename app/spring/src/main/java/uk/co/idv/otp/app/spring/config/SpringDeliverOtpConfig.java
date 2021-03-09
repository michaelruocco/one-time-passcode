package uk.co.idv.otp.app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.otp.adapter.delivery.InMemoryDeliverOtp;
import uk.co.idv.otp.app.plain.config.AppAdapter;
import uk.co.idv.otp.config.delivery.SesDeliveryConfig;
import uk.co.idv.otp.config.delivery.SnsDeliveryConfig;
import uk.co.idv.otp.usecases.send.deliver.CompositeDeliverOtp;
import uk.co.idv.otp.usecases.send.deliver.DeliverOtp;
import uk.co.idv.otp.usecases.send.deliver.DeliverOtpByMethod;

@Configuration
public class SpringDeliverOtpConfig {

    @Profile("!stubbed")
    @Bean
    public DeliverOtp deliverOtp(AppAdapter appAdapter) {
        return new CompositeDeliverOtp(
                snsDeliverOtp(appAdapter),
                sesDeliverOtp(appAdapter)
        );
    }

    @Profile("stubbed")
    @Bean
    public DeliverOtp inMemoryDeliverOtp(AppAdapter appAdapter) {
        return InMemoryDeliverOtp.builder()
                .clock(appAdapter.getClock())
                .uuidGenerator(appAdapter.getUuidGenerator())
                .build();
    }

    private static DeliverOtpByMethod snsDeliverOtp(AppAdapter appAdapter) {
        return SnsDeliveryConfig.builder()
                .clock(appAdapter.getClock())
                .endpointUri(loadSnsEndpointUri())
                .region(loadAwsRegion())
                .build()
                .deliverOtp();
    }

    private static DeliverOtpByMethod sesDeliverOtp(AppAdapter appAdapter) {
        return SesDeliveryConfig.builder()
                .clock(appAdapter.getClock())
                .endpointUri(loadSesEndpointUri())
                .region(loadAwsRegion())
                .build()
                .deliverOtp();
    }

    private static String loadAwsRegion() {
        return System.getProperty("aws.region", "eu-west-1");
    }

    private static String loadSnsEndpointUri() {
        return System.getProperty("aws.sns.endpoint.uri");
    }

    private static String loadSesEndpointUri() {
        return System.getProperty("aws.ses.endpoint.uri");
    }

}
