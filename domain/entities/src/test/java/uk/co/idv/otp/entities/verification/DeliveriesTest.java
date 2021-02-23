package uk.co.idv.otp.entities.verification;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.delivery.Deliveries;
import uk.co.idv.otp.entities.delivery.DeliveriesMother;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.NoDeliveriesRemainingException;
import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.entities.passcode.Passcodes;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
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

    @Test
    void shouldReturnValidPasscodesFromDeliveries() {
        Instant now = Instant.now();
        Passcode passcode = mock(Passcode.class);
        Delivery delivery1 = givenDeliveryWithValidPasscode(now, passcode);
        Delivery delivery2 = givenDeliveryWithInvalidPasscode(now);
        Deliveries deliveries = DeliveriesMother.withDeliveries(delivery1, delivery2);

        Passcodes passcodes = deliveries.getValidPasscodes(now);

        assertThat(passcodes).containsExactly(passcode);
    }

    private Delivery givenDeliveryWithValidPasscode(Instant now, Passcode passcode) {
        Delivery delivery = mock(Delivery.class);
        given(delivery.getPasscodeIfValid(now)).willReturn(Optional.of(passcode));
        return delivery;
    }

    private Delivery givenDeliveryWithInvalidPasscode(Instant now) {
        Delivery delivery = mock(Delivery.class);
        given(delivery.getPasscodeIfValid(now)).willReturn(Optional.empty());
        return delivery;
    }
}
