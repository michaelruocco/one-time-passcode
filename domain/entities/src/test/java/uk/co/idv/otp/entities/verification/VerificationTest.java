package uk.co.idv.otp.entities.verification;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationTest {

    @Test
    void shouldReturnId() {
        UUID id = UUID.randomUUID();

        Verification verification = Verification.builder()
                .id(id)
                .build();

        assertThat(verification.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnContextId() {
        UUID contextId = UUID.randomUUID();

        Verification verification = Verification.builder()
                .contextId(contextId)
                .build();

        assertThat(verification.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnCreated() {
        Instant created = Instant.now();

        Verification verification = Verification.builder()
                .created(created)
                .build();

        assertThat(verification.getCreated()).isEqualTo(created);
    }

    @Test
    void shouldReturnExpiry() {
        Instant expiry = Instant.now();

        Verification verification = Verification.builder()
                .expiry(expiry)
                .build();

        assertThat(verification.getExpiry()).isEqualTo(expiry);
    }

    @Test
    void shouldReturnDeliveries() {
        Deliveries deliveries = DeliveriesMother.one();

        Verification verification = Verification.builder()
                .deliveries(deliveries)
                .build();

        assertThat(verification.getDeliveries()).isEqualTo(deliveries);
    }

    @Test
    void shouldReturnSuccessful() {
        Verification verification = Verification.builder()
                .successful(true)
                .build();

        assertThat(verification.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnComplete() {
        Verification verification = Verification.builder()
                .complete(true)
                .build();

        assertThat(verification.isComplete()).isTrue();
    }

    @Test
    void shouldAddDelivery() {
        Verification verification = VerificationMother.build();
        Delivery delivery = DeliveryMother.withSent(Instant.now());

        Verification updated = verification.add(delivery);

        assertThat(updated)
                .usingRecursiveComparison()
                .ignoringFields("deliveries")
                .isEqualTo(verification);
        assertThat(updated.getDeliveries())
                .containsAll(verification.getDeliveries())
                .contains(delivery);
    }

}
