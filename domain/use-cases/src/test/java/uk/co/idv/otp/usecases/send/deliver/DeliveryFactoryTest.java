package uk.co.idv.otp.usecases.send.deliver;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.delivery.DefaultDeliveryRequest;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryRequestMother;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class DeliveryFactoryTest {

    private static final Instant NOW = Instant.now();
    private static final Clock CLOCK = Clock.fixed(NOW, ZoneId.systemDefault());

    private final DeliveryFactory factory = new DeliveryFactory(CLOCK);

    @Test
    void shouldPopulateMethodOnDeliveryBuilder() {
        DefaultDeliveryRequest request = DeliveryRequestMother.build();

        Delivery delivery = factory.toDelivery(request).build();

        assertThat(delivery.getMethod()).isEqualTo(request.getMethod());
    }

    @Test
    void shouldPopulateMessageOnDeliveryBuilder() {
        DefaultDeliveryRequest request = DeliveryRequestMother.build();

        Delivery delivery = factory.toDelivery(request).build();

        assertThat(delivery.getMessage()).isEqualTo(request.getMessage());
    }

    @Test
    void shouldPopulateSentAsCurrentTimeOnDeliveryBuilder() {
        Delivery delivery = factory.toDelivery(DeliveryRequestMother.build()).build();

        assertThat(delivery.getSent()).isEqualTo(NOW);
    }

}
