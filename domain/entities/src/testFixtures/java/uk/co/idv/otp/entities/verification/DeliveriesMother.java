package uk.co.idv.otp.entities.verification;

import java.util.Collections;

public interface DeliveriesMother {

    static Deliveries one() {
        return builder().build();
    }

    static Deliveries.DeliveriesBuilder builder() {
        return Deliveries.builder()
                .max(3)
                .values(Collections.singleton(DeliveryMother.build()));
    }

}
