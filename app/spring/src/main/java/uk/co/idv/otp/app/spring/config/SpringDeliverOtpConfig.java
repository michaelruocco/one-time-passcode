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
import uk.co.idv.otp.usecases.send.deliver.DeliveryFactory;

@Configuration
public class SpringDeliverOtpConfig {

    @Profile("!stubbed")
    @Bean
    public DeliverOtp deliverOtp(AppAdapter appAdapter) {
        return new CompositeDeliverOtp(
                new DeliveryFactory(appAdapter.getClock()),
                snsDeliverOtp(),
                sesDeliverOtp()
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

    private static DeliverOtpByMethod snsDeliverOtp() {
        return SnsDeliveryConfig.builder()
                .senderId(loadSnsSenderId())
                .endpointUri(loadSnsEndpointUri())
                .region(loadAwsRegion())
                .build()
                .deliverOtp();
    }

    private static DeliverOtpByMethod sesDeliverOtp() {
        return SesDeliveryConfig.builder()
                .sourceEmailAddress(loadSesSourceEmailAddress())
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

    private static String loadSnsSenderId() {
        return System.getProperty("aws.sns.sender.id", "IDV Demo");
    }

    private static String loadSesEndpointUri() {
        return System.getProperty("aws.ses.endpoint.uri");
    }

    private static String loadSesSourceEmailAddress() {
        return System.getProperty("aws.ses.source.email.address", "idv.demo.mail@gmail.com");
    }

}
