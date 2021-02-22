package uk.co.idv.otp.entities.passcode;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Data
public class Passcodes implements Iterable<Passcode> {

    private final Collection<Passcode> values;

    public Passcodes(Passcode... values) {
        this(Arrays.asList(values));
    }

    @Override
    public Iterator<Passcode> iterator() {
        return values.iterator();
    }

    public Passcodes getActive(Instant now) {
        return new Passcodes(values.stream()
                .filter(passcode -> !passcode.hasExpired(now))
                .collect(Collectors.toList())
        );
    }

    public boolean anyValid(Collection<String> inputPasscodes) {
        return inputPasscodes.stream().anyMatch(this::isValid);
    }

    private boolean isValid(String inputPasscode) {
        return values.stream().anyMatch(passcode -> passcode.isValid(inputPasscode));
    }

}
