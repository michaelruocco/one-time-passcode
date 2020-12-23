package uk.co.idv.otp.entities.send;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.idv.otp.entities.send.message.MessageMother;

import static org.assertj.core.api.Assertions.assertThat;

class DeliveryRequestTest {

    @Test
    void shouldReturnMethod() {
        DeliveryMethod method = DeliveryMethodMother.build();

        DeliveryRequest request = DeliveryRequest.builder()
                .method(method)
                .build();

        assertThat(request.getMethod()).isEqualTo(method);
    }

    @Test
    void shouldReturnMessage() {
        Message message = MessageMother.build();

        DeliveryRequest request = DeliveryRequest.builder()
                .message(message)
                .build();

        assertThat(request.getMessage()).isEqualTo(message);
    }

}
