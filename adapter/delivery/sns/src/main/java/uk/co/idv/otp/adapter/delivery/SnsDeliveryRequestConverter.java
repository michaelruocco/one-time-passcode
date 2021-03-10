package uk.co.idv.otp.adapter.delivery;

import com.amazonaws.services.sns.model.PublishRequest;
import lombok.RequiredArgsConstructor;
import uk.co.idv.otp.adapter.delivery.attributes.MessageAttributeBuilder;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;

@RequiredArgsConstructor
public class SnsDeliveryRequestConverter {

    private final MessageAttributeBuilder attributeBuilder;

    public PublishRequest toPublishRequest(DeliveryRequest request) {
        return new PublishRequest()
                .withMessage(request.getMessageText())
                .withPhoneNumber(request.getDeliveryMethodValue())
                .withMessageAttributes(attributeBuilder.build());
    }

    public static SnsDeliveryRequestConverter build(String senderId) {
        return new SnsDeliveryRequestConverter(new MessageAttributeBuilder(senderId));
    }

}
