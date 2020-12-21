package uk.co.idv.otp.entities.send;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.verification.Message;
import uk.co.idv.otp.entities.verification.MessageMother;
import uk.co.idv.otp.entities.verification.OtpDeliveryMethod;
import uk.co.idv.otp.entities.verification.SmsOtpDeliveryMethodMother;

import static org.assertj.core.api.Assertions.assertThat;

class DeliveryRequestTest {

    @Test
    void shouldReturnMethod() {
        OtpDeliveryMethod method = SmsOtpDeliveryMethodMother.sms();

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
