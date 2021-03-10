package uk.co.idv.otp.entities.delivery;

import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.otp.entities.send.message.MessageMother;

public interface DeliveryRequestMother {

    static DefaultDeliveryRequest build() {
        return builder().build();
    }

    static DefaultDeliveryRequest.DefaultDeliveryRequestBuilder builder() {
        return DefaultDeliveryRequest.builder()
                .method(DeliveryMethodMother.build())
                .message(MessageMother.build());
    }

}
