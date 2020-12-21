package uk.co.idv.otp.entities.send;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SendOtpRequestTest {

    @Test
    void shouldReturnContextId() {
        UUID contextId = UUID.randomUUID();

        SendOtpRequest request = SendOtpRequest.builder()
                .contextId(contextId)
                .build();

        assertThat(request.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnDeliveryMethodId() {
        UUID deliveryMethodId = UUID.randomUUID();

        SendOtpRequest request = SendOtpRequest.builder()
                .deliveryMethodId(deliveryMethodId)
                .build();

        assertThat(request.getDeliveryMethodId()).isEqualTo(deliveryMethodId);
    }

}
