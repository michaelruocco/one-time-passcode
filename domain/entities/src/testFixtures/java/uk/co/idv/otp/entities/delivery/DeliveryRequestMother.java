package uk.co.idv.otp.entities.delivery;

import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.otp.entities.send.message.MessageMother;

public interface DeliveryRequestMother {

    static DeliveryRequest build() {
        return builder().build();
    }

    static DeliveryRequest.DeliveryRequestBuilder builder() {
        return DeliveryRequest.builder()
                .method(DeliveryMethodMother.build())
                .message(MessageMother.build());
    }

}
