package uk.co.idv.otp.entities.verification;

import uk.co.idv.otp.entities.delivery.Delivery;

import java.time.Instant;

public interface DeliveryMother {

    static Delivery build() {
        return builder().build();
    }

    static Delivery withSent(Instant sent) {
        return builder().sent(sent).build();
    }

    static Delivery.DeliveryBuilder builder() {
        return Delivery.builder()
                .method(SmsOtpDeliveryMethodMother.sms())
                .message(MessageMother.build())
                .messageId("ABC-123")
                .sent(Instant.parse("2020-09-14T20:03:02.002Z"));
    }

}
