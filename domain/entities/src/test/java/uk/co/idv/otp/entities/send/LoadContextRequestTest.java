package uk.co.idv.otp.entities.send;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LoadContextRequestTest {

    @Test
    void shouldReturnContextId() {
        UUID contextId = UUID.randomUUID();

        LoadContextRequest request = LoadContextRequest.builder()
                .contextId(contextId)
                .build();

        assertThat(request.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnDeliveryMethodId() {
        UUID deliveryMethodId = UUID.randomUUID();

        LoadContextRequest request = LoadContextRequest.builder()
                .deliveryMethodId(deliveryMethodId)
                .build();

        assertThat(request.getDeliveryMethodId()).isEqualTo(deliveryMethodId);
    }

}
