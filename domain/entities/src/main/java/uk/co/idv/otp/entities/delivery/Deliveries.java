package uk.co.idv.otp.entities.delivery;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.collections4.IterableUtils;
import uk.co.idv.otp.entities.passcode.Passcodes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder(toBuilder = true)
@Data
public class Deliveries implements Iterable<Delivery> {

    private final int max;

    @Builder.Default
    private final Collection<Delivery> values = Collections.emptyList();

    @Override
    public Iterator<Delivery> iterator() {
        return values.iterator();
    }

    public Stream<Delivery> stream() {
        return values.stream();
    }

    public Delivery first() {
        return IterableUtils.first(values);
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

    public Passcodes getValidPasscodes(Instant now) {
        return new Passcodes(values.stream()
                .map(delivery -> delivery.getPasscodeIfValid(now))
                .flatMap(Optional::stream)
                .collect(Collectors.toList())
        );
    }

}
