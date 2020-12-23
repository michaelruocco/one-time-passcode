package uk.co.idv.otp.entities.delivery;

import uk.co.idv.otp.entities.send.message.MessageMother;

public interface DeliveryRequestMother {

    static DeliveryRequest build() {
        return builder().build();
    }

    static DeliveryRequest.DeliveryRequestBuilder builder() {
        return DeliveryRequest.builder()
                .method(SmsOtpDeliveryMethodMother.sms())
                .message(MessageMother.build());
    }

}
