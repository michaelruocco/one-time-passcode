package uk.co.idv.otp.entities.verify;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Collection;

@Builder
@Data
public class AttemptedPasscodes {

    private final Instant timestamp;
    private final Collection<String> values;

}
