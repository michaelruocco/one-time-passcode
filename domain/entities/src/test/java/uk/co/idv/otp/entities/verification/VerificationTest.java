package uk.co.idv.otp.entities.verification;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.Verification;
import uk.co.idv.otp.entities.VerificationMother;
import uk.co.idv.otp.entities.delivery.Deliveries;
import uk.co.idv.otp.entities.delivery.DeliveriesMother;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryMother;
import uk.co.idv.otp.entities.send.message.Message;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

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
    void shouldReturnFirstDelivery() {
        Delivery delivery1 = mock(Delivery.class);
        Delivery delivery2 = mock(Delivery.class);
        Deliveries deliveries = DeliveriesMother.withDeliveries(delivery1, delivery2);

        Verification verification = Verification.builder()
                .deliveries(deliveries)
                .build();

        assertThat(verification.getFirstDelivery()).isEqualTo(delivery1);
    }

    @Test
    void shouldReturnMessageFromFirstDelivery() {
        Message message1 = mock(Message.class);
        Message message2 = mock(Message.class);
        Deliveries deliveries = DeliveriesMother.withDeliveries(
                givenDeliveryWithMessage(message1),
                givenDeliveryWithMessage(message2)
        );

        Verification verification = Verification.builder()
                .deliveries(deliveries)
                .build();

        assertThat(verification.getFirstMessage()).isEqualTo(message1);
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

    private Delivery givenDeliveryWithMessage(Message message) {
        Delivery delivery = mock(Delivery.class);
        given(delivery.getMessage()).willReturn(message);
        return delivery;
    }

}
