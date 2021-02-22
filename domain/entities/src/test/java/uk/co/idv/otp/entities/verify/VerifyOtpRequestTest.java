package uk.co.idv.otp.entities.verify;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VerifyOtpRequestTest {

    @Test
    void shouldReturnId() {
        UUID id = UUID.randomUUID();

        VerifyOtpRequest request = VerifyOtpRequest.builder()
                .id(id)
                .build();

        assertThat(request.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnPasscodes() {
        Collection<String> passcodes = Collections.emptyList();

        VerifyOtpRequest request = VerifyOtpRequest.builder()
                .passcodes(passcodes)
                .build();

        assertThat(request.getPasscodes()).isEqualTo(passcodes);
    }

}
