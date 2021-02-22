package uk.co.idv.otp.entities.passcode;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class Passcode {

    private final String value;
    private final Instant created;
    private final Instant expiry;

    public boolean hasExpired(Instant now) {
        return now.isAfter(expiry);
    }

    public boolean isValid(String value) {
        return this.value.equals(value);
    }

}
