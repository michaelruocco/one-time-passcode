package uk.co.idv.otp.entities.verification;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

@Builder
@Data
public class Deliveries implements Iterable<Delivery> {

    private final int max;

    @Builder.Default
    private final Collection<Delivery> values = Collections.emptyList();

    @Override
    public Iterator<Delivery> iterator() {
        return values.iterator();
    }

    public Deliveries add(Delivery delivery) {
        if (values.size() >= max) {
            throw new NoDeliveriesRemainingException(max);
        }
        Collection<Delivery> updated = new ArrayList<>(values);
        updated.add(delivery);
        return Deliveries.builder()
                .max(max)
                .values(updated)
                .build();
    }

}
