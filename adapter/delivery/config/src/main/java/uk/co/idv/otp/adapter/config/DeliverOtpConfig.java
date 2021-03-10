package uk.co.idv.otp.adapter.config;

import lombok.Builder;
import uk.co.idv.otp.adapter.delivery.InMemoryDeliverOtp;
import uk.co.idv.otp.usecases.send.deliver.CompositeDeliverOtp;
import uk.co.idv.otp.usecases.send.deliver.DeliverOtp;
import uk.co.idv.otp.usecases.send.deliver.DeliverOtpByMethod;
import uk.co.idv.otp.usecases.send.deliver.DeliveryFactory;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

import java.time.Clock;

@Builder
public class DeliverOtpConfig {

    private final Clock clock;
    private final UuidGenerator uuidGenerator;
    private final String awsRegion;
    private final String snsEndpointUri;
    private final String snsSenderId;
    private final String sesEndpointUri;
    private final String sesSourceEmailAddress;

    public DeliverOtp deliverOtp() {
        return new CompositeDeliverOtp(
                new DeliveryFactory(clock),
                snsDeliverOtp(),
                sesDeliverOtp()
        );
    }

    public DeliverOtp inMemoryDeliverOtp() {
        return InMemoryDeliverOtp.builder()
                .clock(clock)
                .uuidGenerator(uuidGenerator)
                .build();
    }

    private DeliverOtpByMethod snsDeliverOtp() {
        return SnsDeliveryConfig.builder()
                .senderId(snsSenderId)
                .endpointUri(snsEndpointUri)
                .region(awsRegion)
                .build()
                .deliverOtp();
    }

    private DeliverOtpByMethod sesDeliverOtp() {
        return SesDeliveryConfig.builder()
                .sourceEmailAddress(sesSourceEmailAddress)
                .endpointUri(sesEndpointUri)
                .region(awsRegion)
                .build()
                .deliverOtp();
    }

}
