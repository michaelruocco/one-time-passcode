package uk.co.idv.otp.entities.send;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.Verification;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.idv.otp.entities.verification.MessageMother;
import uk.co.idv.otp.entities.verification.VerificationMother;

import static org.assertj.core.api.Assertions.assertThat;

class DeliveryRequestTest {

    @Test
    void shouldReturnMethod() {
        Verification verification = VerificationMother.build();

        DeliveryRequest request = DeliveryRequest.builder()
                .verification(verification)
                .build();

        assertThat(request.getVerification()).isEqualTo(verification);
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
