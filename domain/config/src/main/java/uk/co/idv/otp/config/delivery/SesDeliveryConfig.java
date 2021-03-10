package uk.co.idv.otp.config.delivery;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.otp.adapter.delivery.SesDeliverOtp;
import uk.co.idv.otp.adapter.delivery.SesDeliveryRequestConverter;
import uk.co.idv.otp.usecases.send.deliver.DeliverOtpByMethod;

@Slf4j
@Builder
public class SesDeliveryConfig {

    private final String endpointUri;
    private final String region;
    private final String sourceEmailAddress;

    @Builder.Default
    private final AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();

    public DeliverOtpByMethod deliverOtp() {
        return SesDeliverOtp.builder()
                .requestConverter(new SesDeliveryRequestConverter(sourceEmailAddress))
                .client(buildClient())
                .build();
    }

    private AmazonSimpleEmailService buildClient() {
        return build(getEndpointConfiguration());
    }

    private EndpointConfiguration getEndpointConfiguration() {
        return new EndpointConfiguration(endpointUri, region);
    }

    private AmazonSimpleEmailService build(EndpointConfiguration endpointConfiguration) {
        log.info("connecting to ses service endpoint {}", endpointConfiguration.getServiceEndpoint());
        log.info("connecting to ses signing region {}", endpointConfiguration.getSigningRegion());
        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }

}
