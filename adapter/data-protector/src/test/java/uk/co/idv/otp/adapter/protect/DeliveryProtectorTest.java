package uk.co.idv.otp.adapter.protect;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryMother;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DeliveryProtectorTest {

    private final UnaryOperator<DeliveryMethod> deliveryMethodProtector = mock(UnaryOperator.class);

    private final DeliveryProtector protector = new DeliveryProtector(deliveryMethodProtector);

    @Test
    void shouldProtectDeliveryMethods() {
        Delivery delivery = DeliveryMother.build();
        DeliveryMethod protectedMethod = mock(DeliveryMethod.class);
        given(deliveryMethodProtector.apply(delivery.getMethod())).willReturn(protectedMethod);

        Delivery protectedDelivery = protector.apply(delivery);

        assertThat(protectedDelivery.getMethod()).isEqualTo(protectedMethod);
        assertThat(protectedDelivery).usingRecursiveComparison()
                .ignoringFields("method")
                .isEqualTo(delivery);
    }

}
