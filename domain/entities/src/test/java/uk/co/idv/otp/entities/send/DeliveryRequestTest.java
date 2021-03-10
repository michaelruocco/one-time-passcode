package uk.co.idv.otp.entities.send;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.otp.entities.delivery.DefaultDeliveryRequest;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.idv.otp.entities.send.message.MessageMother;

import static org.assertj.core.api.Assertions.assertThat;

class DeliveryRequestTest {

    @Test
    void shouldReturnMethod() {
        DeliveryMethod method = DeliveryMethodMother.build();

        DefaultDeliveryRequest request = DefaultDeliveryRequest.builder()
                .method(method)
                .build();

        assertThat(request.getMethod()).isEqualTo(method);
    }

    @Test
    void shouldReturnTypeFromMethod() {
        DeliveryMethod method = DeliveryMethodMother.build();

        DefaultDeliveryRequest request = DefaultDeliveryRequest.builder()
                .method(method)
                .build();

        assertThat(request.getDeliveryMethodType()).isEqualTo(method.getType());
    }

    @Test
    void shouldReturnValueFromMethod() {
        DeliveryMethod method = DeliveryMethodMother.build();

        DeliveryRequest request = DefaultDeliveryRequest.builder()
                .method(method)
                .build();

        assertThat(request.getDeliveryMethodValue()).isEqualTo(method.getValue());
    }

    @Test
    void shouldReturnMessage() {
        Message message = MessageMother.build();

        DefaultDeliveryRequest request = DefaultDeliveryRequest.builder()
                .message(message)
                .build();

        assertThat(request.getMessage()).isEqualTo(message);
    }

    @Test
    void shouldReturnMessageText() {
        Message message = MessageMother.build();

        DeliveryRequest request = DefaultDeliveryRequest.builder()
                .message(message)
                .build();

        assertThat(request.getMessageText()).isEqualTo(message.getText());
    }

}
