package uk.co.idv.otp.entities.verification;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Builder(toBuilder = true)
@Data
public class Verification {

    private final UUID id;
    private final UUID contextId;
    private final Instant created;
    private final Instant expiry;
    private final Deliveries deliveries;
    private final boolean successful;
    private final boolean complete;

    public Verification add(Delivery delivery) {
        return toBuilder()
                .deliveries(deliveries.add(delivery))
                .build();
    }

    public Delivery getFirstDelivery() {
        return deliveries.first();
    }

}
