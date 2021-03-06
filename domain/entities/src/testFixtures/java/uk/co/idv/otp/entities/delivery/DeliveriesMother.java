package uk.co.idv.otp.entities.delivery;

import java.util.Arrays;
import java.util.Collections;

public interface DeliveriesMother {

    static Deliveries one() {
        return builder().build();
    }

    static Deliveries empty(int maxNumberOfDeliveries) {
        return builder()
                .max(maxNumberOfDeliveries)
                .values(Collections.emptyList())
                .build();
    }

    static Deliveries empty() {
        return withDeliveries();
    }

    static Deliveries withDeliveries(Delivery... deliveries) {
        return builder().values(Arrays.asList(deliveries)).build();
    }

    static Deliveries.DeliveriesBuilder builder() {
        return Deliveries.builder()
                .max(2)
                .values(Collections.singleton(DeliveryMother.build()));
    }

}
