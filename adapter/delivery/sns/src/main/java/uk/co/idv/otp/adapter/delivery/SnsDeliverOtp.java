package uk.co.idv.otp.adapter.delivery;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.usecases.send.DeliverOtp;

import java.time.Clock;

@Builder
@Slf4j
public class SnsDeliverOtp implements DeliverOtp {

    private final AmazonSNS client;
    private final Clock clock;

    @Builder.Default
    private final DeliveryRequestConverter converter = new DeliveryRequestConverter();

    @Override
    public Delivery deliver(DeliveryRequest deliveryRequest) {
        PublishRequest publishRequest = converter.toPublishRequest(deliveryRequest);
        log.debug("sending publish request {}", publishRequest);
        PublishResult result = client.publish(publishRequest);
        log.info("message {} sent", result.getMessageId());
        return toDelivery(deliveryRequest)
                .messageId(result.getMessageId())
                .build();
    }

    private Delivery.DeliveryBuilder toDelivery(DeliveryRequest request) {
        return Delivery.builder()
                .method(request.getMethod())
                .message(request.getMessage())
                .sent(clock.instant());
    }

}
