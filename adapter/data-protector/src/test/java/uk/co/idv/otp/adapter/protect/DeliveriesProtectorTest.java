package uk.co.idv.otp.adapter.protect;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.delivery.Deliveries;
import uk.co.idv.otp.entities.delivery.DeliveriesMother;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryMother;

import java.time.Instant;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DeliveriesProtectorTest {

    private static final Instant NOW = Instant.now();

    private final UnaryOperator<Delivery> deliveryProtector = mock(UnaryOperator.class);

    private final DeliveriesProtector protector = new DeliveriesProtector(deliveryProtector);

    @Test
    void shouldProtectDeliveryMethods() {
        Delivery delivery1 = DeliveryMother.withSent(NOW.plusSeconds(10));
        Delivery delivery2 = DeliveryMother.withSent(NOW.plusSeconds(20));
        Deliveries deliveries = DeliveriesMother.withDeliveries(delivery1, delivery2);
        Delivery protected1 = mock(Delivery.class);
        Delivery protected2 = mock(Delivery.class);
        given(deliveryProtector.apply(delivery1)).willReturn(protected1);
        given(deliveryProtector.apply(delivery2)).willReturn(protected2);

        Deliveries protectedDeliveries = protector.apply(deliveries);

        assertThat(protectedDeliveries).containsExactly(protected1, protected2);
        assertThat(protectedDeliveries.getMax()).isEqualTo(deliveries.getMax());
    }

}
