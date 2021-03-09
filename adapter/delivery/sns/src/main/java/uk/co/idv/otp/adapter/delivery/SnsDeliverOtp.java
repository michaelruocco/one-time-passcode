package uk.co.idv.otp.adapter.delivery;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.usecases.send.deliver.DeliverOtpByMethod;
import uk.co.idv.otp.usecases.send.deliver.DeliveryFactory;

@Builder
@Slf4j
public class SnsDeliverOtp implements DeliverOtpByMethod {

    private final AmazonSNS client;
    private final DeliveryFactory deliveryFactory;

    @Builder.Default
    private final SnsDeliveryRequestConverter converter = new SnsDeliveryRequestConverter();

    @Override
    public String getDeliveryMethodName() {
        return "sms";
    }

    @Override
    public Delivery deliver(DeliveryRequest deliveryRequest) {
        PublishRequest publishRequest = converter.toPublishRequest(deliveryRequest);
        log.debug("sending publish request {}", publishRequest);
        PublishResult result = client.publish(publishRequest);
        log.info("message {} sent", result.getMessageId());
        return deliveryFactory.toDelivery(deliveryRequest)
                .messageId(result.getMessageId())
                .build();
    }

}
