package uk.co.idv.otp.adapter.delivery;

import com.amazonaws.services.sns.model.PublishRequest;
import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.otp.adapter.delivery.attributes.MessageAttributeBuilder;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.entities.send.message.Message;

@RequiredArgsConstructor
public class DeliveryRequestConverter {

    private final MessageAttributeBuilder attributeBuilder;

    public PublishRequest toPublishRequest(DeliveryRequest request) {
        Message message = request.getMessage();
        DeliveryMethod method = request.getMethod();
        return new PublishRequest()
                .withMessage(message.getText())
                .withPhoneNumber(method.getValue())
                .withMessageAttributes(attributeBuilder.build());
    }

}
