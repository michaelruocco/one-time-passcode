package uk.co.idv.otp.entities.verification;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.delivery.OtpDeliveryMethod;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OtpDeliveryMethodTest {

    @Test
    void shouldReturnId() {
       UUID id = UUID.randomUUID();

        OtpDeliveryMethod method = OtpDeliveryMethod.builder()
                .id(id)
                .build();

        assertThat(method.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnType() {
        String type = "sms";

        OtpDeliveryMethod method = OtpDeliveryMethod.builder()
                .type(type)
                .build();

        assertThat(method.getType()).isEqualTo(type);
    }

    @Test
    void shouldReturnValue() {
        String value = "+447809123456";

        OtpDeliveryMethod method = OtpDeliveryMethod.builder()
                .value(value)
                .build();

        assertThat(method.getValue()).isEqualTo(value);
    }

}
