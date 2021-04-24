package uk.co.idv.otp.adapter.protect;

import lombok.Builder;
import uk.co.idv.method.adapter.otp.protect.mask.DeliveryMethodMasker;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.delivery.Deliveries;

import java.util.function.UnaryOperator;

@Builder
public class OtpVerificationProtector implements UnaryOperator<OtpVerification> {

    @Builder.Default
    private final UnaryOperator<DeliveryMethod> deliveryMethodProtector = new DeliveryMethodMasker();

    @Builder.Default
    private final UnaryOperator<Deliveries> deliveriesProtector = new DeliveriesProtector();

    @Override
    public OtpVerification apply(OtpVerification verification) {
        return verification.toBuilder()
                .deliveryMethod(deliveryMethodProtector.apply(verification.getDeliveryMethod()))
                .deliveries(deliveriesProtector.apply(verification.getDeliveries()))
                .build();
    }

}
