package uk.co.idv.otp.adapter.protect;

import lombok.RequiredArgsConstructor;
import uk.co.idv.method.adapter.otp.protect.mask.DeliveryMethodMasker;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.otp.entities.delivery.Delivery;

import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class DeliveryProtector implements UnaryOperator<Delivery> {

    private final UnaryOperator<DeliveryMethod> methodProtector;

    public DeliveryProtector() {
        this(new DeliveryMethodMasker());
    }

    @Override
    public Delivery apply(Delivery delivery) {
        return delivery.toBuilder()
                .method(methodProtector.apply(delivery.getMethod()))
                .build();
    }

}
