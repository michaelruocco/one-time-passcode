package uk.co.idv.otp.adapter.delivery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.delivery.DefaultDeliveryRequest;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryRequestMother;
import uk.co.idv.otp.usecases.send.deliver.DeliverOtp;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

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

    private final UuidGenerator uuidGenerator = mock(UuidGenerator.class);
    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());

    private final DeliverOtp deliverOtp = InMemoryDeliverOtp.builder()
            .uuidGenerator(uuidGenerator)
            .clock(clock)
            .build();

    @BeforeEach
    void setUp() {
        given(uuidGenerator.generate()).willReturn(MESSAGE_ID);
    }

    @Test
    void shouldReturnDeliveryWithDeliveryMethod() {
        DefaultDeliveryRequest request = DeliveryRequestMother.build();

        Delivery delivery = deliverOtp.deliver(request);

        assertThat(delivery.getMethod()).isEqualTo(request.getMethod());
    }

    @Test
    void shouldReturnDeliveryWithMessage() {
        DefaultDeliveryRequest request = DeliveryRequestMother.build();

        Delivery delivery = deliverOtp.deliver(request);

        assertThat(delivery.getMessage()).isEqualTo(request.getMessage());
    }

    @Test
    void shouldReturnDeliveryWithGeneratedMessageIdAsString() {
        DefaultDeliveryRequest request = DeliveryRequestMother.build();

        Delivery delivery = deliverOtp.deliver(request);

        assertThat(delivery.getMessageId()).isEqualTo(MESSAGE_ID.toString());
    }

    @Test
    void shouldReturnDeliveryWithSentTimestamp() {
        DefaultDeliveryRequest request = DeliveryRequestMother.build();

        Delivery delivery = deliverOtp.deliver(request);

        assertThat(delivery.getSent()).isEqualTo(NOW);
    }

}
