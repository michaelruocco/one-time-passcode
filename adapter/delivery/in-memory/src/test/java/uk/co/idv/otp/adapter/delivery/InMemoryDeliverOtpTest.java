package uk.co.idv.otp.adapter.delivery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.entities.delivery.DeliveryRequestMother;
import uk.co.idv.otp.usecases.send.DeliverOtp;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class InMemoryDeliverOtpTest {

    private static final Instant NOW = Instant.now();
    private static final UUID MESSAGE_ID = UUID.randomUUID();

    private final IdGenerator idGenerator = mock(IdGenerator.class);
    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());

    private final DeliverOtp deliverOtp = new InMemoryDeliverOtp(idGenerator, clock);

    @BeforeEach
    void setUp() {
        given(idGenerator.generate()).willReturn(MESSAGE_ID);
    }

    @Test
    void shouldReturnDeliveryWithDeliveryMethod() {
        DeliveryRequest request = DeliveryRequestMother.build();

        Delivery delivery = deliverOtp.deliver(request);

        assertThat(delivery.getMethod()).isEqualTo(request.getMethod());
    }

    @Test
    void shouldReturnDeliveryWithMessage() {
        DeliveryRequest request = DeliveryRequestMother.build();

        Delivery delivery = deliverOtp.deliver(request);

        assertThat(delivery.getMessage()).isEqualTo(request.getMessage());
    }

    @Test
    void shouldReturnDeliveryWithGeneratedMessageIdAsString() {
        DeliveryRequest request = DeliveryRequestMother.build();

        Delivery delivery = deliverOtp.deliver(request);

        assertThat(delivery.getMessageId()).isEqualTo(MESSAGE_ID.toString());
    }

    @Test
    void shouldReturnDeliveryWithSentTimestamp() {
        DeliveryRequest request = DeliveryRequestMother.build();

        Delivery delivery = deliverOtp.deliver(request);

        assertThat(delivery.getSent()).isEqualTo(NOW);
    }

}
