package uk.co.idv.otp.entities.verification;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.OtpVerificationMother;
import uk.co.idv.otp.entities.delivery.Deliveries;
import uk.co.idv.otp.entities.delivery.DeliveriesMother;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryMother;
import uk.co.idv.otp.entities.send.message.Message;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OtpVerificationTest {

    @Test
    void shouldReturnId() {
        UUID id = UUID.randomUUID();

        OtpVerification verification = OtpVerification.builder()
                .id(id)
                .build();

        assertThat(verification.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnContextId() {
        UUID contextId = UUID.randomUUID();

        OtpVerification verification = OtpVerification.builder()
                .contextId(contextId)
                .build();

        assertThat(verification.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnCreated() {
        Instant created = Instant.now();

        OtpVerification verification = OtpVerification.builder()
                .created(created)
                .build();

        assertThat(verification.getCreated()).isEqualTo(created);
    }

    @Test
    void shouldReturnExpiry() {
        Instant expiry = Instant.now();

        OtpVerification verification = OtpVerification.builder()
                .expiry(expiry)
                .build();

        assertThat(verification.getExpiry()).isEqualTo(expiry);
    }

    @Test
    void shouldReturnDeliveries() {
        Deliveries deliveries = DeliveriesMother.one();

        OtpVerification verification = OtpVerification.builder()
                .deliveries(deliveries)
                .build();

        assertThat(verification.getDeliveries()).isEqualTo(deliveries);
    }

    @Test
    void shouldReturnFirstDelivery() {
        Delivery delivery1 = mock(Delivery.class);
        Delivery delivery2 = mock(Delivery.class);
        Deliveries deliveries = DeliveriesMother.withDeliveries(delivery1, delivery2);

        OtpVerification verification = OtpVerification.builder()
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

        OtpVerification verification = OtpVerification.builder()
                .deliveries(deliveries)
                .build();

        assertThat(verification.getFirstMessage()).isEqualTo(message1);
    }

    @Test
    void shouldReturnProtectSensitiveData() {
        OtpVerification verification = OtpVerification.builder()
                .protectSensitiveData(true)
                .build();

        assertThat(verification.isProtectSensitiveData()).isTrue();
    }

    @Test
    void shouldReturnSuccessful() {
        OtpVerification verification = OtpVerification.builder()
                .successful(true)
                .build();

        assertThat(verification.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnComplete() {
        OtpVerification verification = OtpVerification.builder()
                .complete(true)
                .build();

        assertThat(verification.isComplete()).isTrue();
    }

    @Test
    void shouldAddDelivery() {
        OtpVerification verification = OtpVerificationMother.build();
        Delivery delivery = DeliveryMother.withSent(Instant.now());

        OtpVerification updated = verification.add(delivery);

        assertThat(updated)
                .usingRecursiveComparison()
                .ignoringFields("deliveries")
                .isEqualTo(verification);
        assertThat(updated.getDeliveries())
                .containsAll(verification.getDeliveries())
                .contains(delivery);
    }

    @Test
    void shouldReturnTrueIfHasExpired() {
        Instant expiry = Instant.now();
        OtpVerification verification = OtpVerificationMother.withExpiry(expiry);
        Instant now = expiry.plus(Duration.ofMillis(1));

        boolean expired = verification.hasExpired(now);

        assertThat(expired).isTrue();
    }

    @Test
    void shouldReturnFalseIfHasNotExpired() {
        Instant expiry = Instant.now();
        OtpVerification verification = OtpVerificationMother.withExpiry(expiry);

        boolean expired = verification.hasExpired(expiry);

        assertThat(expired).isFalse();
    }

    private Delivery givenDeliveryWithMessage(Message message) {
        Delivery delivery = mock(Delivery.class);
        given(delivery.getMessage()).willReturn(message);
        return delivery;
    }

}
