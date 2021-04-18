package uk.co.idv.otp.adapter.protect;

import lombok.RequiredArgsConstructor;
import uk.co.idv.otp.entities.delivery.Deliveries;
import uk.co.idv.otp.entities.delivery.Delivery;

import java.util.Collection;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DeliveriesProtector implements UnaryOperator<Deliveries> {

    private final UnaryOperator<Delivery> deliveryProtector;

    public DeliveriesProtector() {
        this(new DeliveryProtector());
    }

    @Override
    public Deliveries apply(Deliveries deliveries) {
        return deliveries.toBuilder()
                .values(protect(deliveries))
                .build();
    }

    public Collection<Delivery> protect(Deliveries deliveries) {
        return deliveries.stream()
                .map(deliveryProtector)
                .collect(Collectors.toList());
    }

}
