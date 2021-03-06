package uk.co.idv.otp.entities.delivery;

import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.idv.otp.entities.send.message.MessageMother;

import java.time.Instant;

public interface DeliveryMother {

    static Delivery build() {
        return builder().build();
    }

    static Delivery withSent(Instant sent) {
        return builder().sent(sent).build();
    }

    static Delivery withMessage(Message message) {
        return builder().message(message).build();
    }

    static Delivery.DeliveryBuilder builder() {
        return Delivery.builder()
                .method(DeliveryMethodMother.build())
                .message(MessageMother.build())
                .messageId("ABC-123")
                .sent(Instant.parse("2020-09-14T20:03:02.002Z"));
    }

}
