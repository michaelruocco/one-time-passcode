package uk.co.idv.otp.adapter.protect;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.OtpVerificationMother;
import uk.co.idv.otp.entities.delivery.Deliveries;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OtpVerificationProtectorTest {

    private final UnaryOperator<DeliveryMethod> deliveryMethodProtector = mock(UnaryOperator.class);
    private final UnaryOperator<Deliveries> deliveriesProtector = mock(UnaryOperator.class);

    private final OtpVerificationProtector protector = OtpVerificationProtector.builder()
            .deliveryMethodProtector(deliveryMethodProtector)
            .deliveriesProtector(deliveriesProtector)
            .build();

    @Test
    void shouldProtectDeliveryMethods() {
        OtpVerification verification = OtpVerificationMother.incomplete();
        DeliveryMethod protectedDeliveryMethod = mock(DeliveryMethod.class);
        given(deliveryMethodProtector.apply(verification.getDeliveryMethod())).willReturn(protectedDeliveryMethod);

        OtpVerification protectedVerification = protector.apply(verification);

        assertThat(protectedVerification.getDeliveryMethod()).isEqualTo(protectedDeliveryMethod);
        assertThat(protectedVerification).usingRecursiveComparison()
                .ignoringFields("deliveryMethod", "deliveries")
                .isEqualTo(verification);
    }

    @Test
    void shouldProtectDeliveries() {
        OtpVerification verification = OtpVerificationMother.incomplete();
        Deliveries protectedDeliveries = mock(Deliveries.class);
        given(deliveriesProtector.apply(verification.getDeliveries())).willReturn(protectedDeliveries);

        OtpVerification protectedVerification = protector.apply(verification);

        assertThat(protectedVerification.getDeliveries()).isEqualTo(protectedDeliveries);
        assertThat(protectedVerification).usingRecursiveComparison()
                .ignoringFields("deliveryMethod", "deliveries")
                .isEqualTo(verification);
    }

}
