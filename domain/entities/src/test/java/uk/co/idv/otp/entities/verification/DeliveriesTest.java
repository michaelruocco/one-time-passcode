package uk.co.idv.otp.entities.verification;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

class DeliveriesTest {

    @Test
    void shouldReturnMax() {
        int max = 3;

        Deliveries deliveries = Deliveries.builder()
                .max(max)
                .build();

        assertThat(deliveries.getMax()).isEqualTo(max);
    }

    @Test
    void shouldReturnValues() {
        Collection<Delivery> values = Collections.singleton(mock(Delivery.class));

        Deliveries deliveries = Deliveries.builder()
                .values(values)
                .build();

        assertThat(deliveries.getValues()).isEqualTo(values);
    }

    @Test
    void shouldBeIterable() {
        Delivery delivery1 = mock(Delivery.class);
        Delivery delivery2 = mock(Delivery.class);

        Deliveries deliveries = Deliveries.builder()
                .values(Arrays.asList(delivery1, delivery2))
                .build();

        assertThat(deliveries).containsExactly(delivery1, delivery2);
    }

    @Test
    void shouldReturnFirstDelivery() {
        Delivery delivery1 = mock(Delivery.class);
        Delivery delivery2 = mock(Delivery.class);
        Deliveries deliveries = Deliveries.builder()
                .values(Arrays.asList(delivery1, delivery2))
                .build();

        Delivery first = deliveries.first();

        assertThat(first).isEqualTo(delivery1);
    }

    @Test
    void shouldAddDeliveryIfDeliveriesRemaining() {
        Delivery existing = mock(Delivery.class);
        Deliveries deliveries = Deliveries.builder()
                .max(2)
                .values(Collections.singleton(existing))
                .build();
        Delivery added = mock(Delivery.class);

        Deliveries updated = deliveries.add(added);

        assertThat(updated).containsExactly(existing, added);
    }

    @Test
    void shouldThrowExceptionIfNoDeliveriesRemaining() {
        Delivery existing = mock(Delivery.class);
        Deliveries deliveries = Deliveries.builder()
                .max(1)
                .values(Collections.singleton(existing))
                .build();
        Delivery added = mock(Delivery.class);

        Throwable error = catchThrowable(() -> deliveries.add(added));

        assertThat(error)
                .isInstanceOf(NoDeliveriesRemainingException.class)
                .hasMessage(Integer.toString(deliveries.getMax()));
    }
}
