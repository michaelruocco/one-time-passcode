package uk.co.idv.otp.entities.verification;

import java.util.Arrays;
import java.util.Collections;

public interface DeliveriesMother {

    static Deliveries one() {
        return builder().build();
    }

    static Deliveries withDeliveries(Delivery... deliveries) {
        return builder().values(Arrays.asList(deliveries)).build();
    }

    static Deliveries.DeliveriesBuilder builder() {
        return Deliveries.builder()
                .max(3)
                .values(Collections.singleton(DeliveryMother.build()));
    }

}
