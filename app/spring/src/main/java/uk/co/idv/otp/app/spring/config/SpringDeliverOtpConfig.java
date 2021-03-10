package uk.co.idv.otp.app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.otp.adapter.config.DeliverOtpConfig;
import uk.co.idv.otp.app.plain.config.AppAdapter;
import uk.co.idv.otp.usecases.send.deliver.DeliverOtp;

@Configuration
public class SpringDeliverOtpConfig {

    @Bean
    public DeliverOtpConfig otpConfig(AppAdapter appAdapter) {
        return DeliverOtpConfig.builder()
                .awsRegion(loadAwsRegion())
                .snsEndpointUri(loadSnsEndpointUri())
                .snsSenderId(loadSnsSenderId())
                .sesEndpointUri(loadSesEndpointUri())
                .sesSourceEmailAddress(loadSesSourceEmailAddress())
                .uuidGenerator(appAdapter.getUuidGenerator())
                .clock(appAdapter.getClock())
                .build();
    }

    @Profile("!stubbed")
    @Bean
    public DeliverOtp deliverOtp(DeliverOtpConfig config) {
        return config.deliverOtp();
    }

    @Profile("stubbed")
    @Bean
    public DeliverOtp inMemoryDeliverOtp(DeliverOtpConfig config) {
        return config.inMemoryDeliverOtp();
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
