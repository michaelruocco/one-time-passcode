package uk.co.idv.otp.entities.verification;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class Passcode {

    private final String value;
    private final Instant created;
    private final Instant expiry;

}
