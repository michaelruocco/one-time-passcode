package uk.co.idv.otp.entities.passcode;

import org.junit.jupiter.api.Test;

import java.time.Duration;
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

    @Test
    void shouldBeExpiredIfNowIsAfterExpiry() {
        Instant now = Instant.now();
        Instant expiry = now.minus(Duration.ofMillis(1));

        Passcode passcode = Passcode.builder()
                .expiry(expiry)
                .build();

        assertThat(passcode.hasExpired(now)).isTrue();
    }

    @Test
    void shouldNotBeExpiredIfNowIsEqualToExpiry() {
        Instant now = Instant.now();

        Passcode passcode = Passcode.builder()
                .expiry(now)
                .build();

        assertThat(passcode.hasExpired(now)).isFalse();
    }

    @Test
    void shouldNotBeExpiredIfNowIsBeforeExpiry() {
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofMillis(1));

        Passcode passcode = Passcode.builder()
                .expiry(expiry)
                .build();

        assertThat(passcode.hasExpired(now)).isFalse();
    }

    @Test
    void shouldNotBeValidIfAttemptedValueDoesNotMatchActualValue() {
        String value = "12345678";

        Passcode passcode = Passcode.builder()
                .value(value)
                .build();

        assertThat(passcode.isValid("11111111")).isFalse();
    }

    @Test
    void shouldBeValidIfAttemptedValueMatchesActualValue() {
        String value = "12345678";

        Passcode passcode = Passcode.builder()
                .value(value)
                .build();

        assertThat(passcode.isValid(value)).isTrue();
    }

}
