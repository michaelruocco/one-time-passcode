package uk.co.idv.otp.entities.verification;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.delivery.NoDeliveriesRemainingException;

import static org.assertj.core.api.Assertions.assertThat;

class NoDeliveriesRemainingExceptionTest {

    @Test
    void shouldReturnMaxAsMessage() {
        int max = 3;

        Throwable error = new NoDeliveriesRemainingException(max);

        assertThat(error.getMessage()).isEqualTo(Integer.toString(max));
    }

}
