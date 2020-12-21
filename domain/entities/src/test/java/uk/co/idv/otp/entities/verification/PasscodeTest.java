package uk.co.idv.otp.entities.verification;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class PasscodeTest {

    @Test
    void shouldReturnValue() {
        String value = "12345678";

        Passcode passcode = Passcode.builder()
                .value(value)
                .build();

        assertThat(passcode.getValue()).isEqualTo(value);
    }

    @Test
    void shouldReturnCreated() {
        Instant created = Instant.now();

        Passcode passcode = Passcode.builder()
                .created(created)
                .build();

        assertThat(passcode.getCreated()).isEqualTo(created);
    }

    @Test
    void shouldReturnExpiry() {
        Instant expiry = Instant.now();

        Passcode passcode = Passcode.builder()
                .expiry(expiry)
                .build();

        assertThat(passcode.getExpiry()).isEqualTo(expiry);
    }

}
