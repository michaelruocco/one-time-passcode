package uk.co.idv.otp.entities.verify;

import java.time.Instant;
import java.util.Arrays;

public interface AttemptedPasscodesMother {

    static AttemptedPasscodes build() {
        return builder().build();
    }

    static AttemptedPasscodes.AttemptedPasscodesBuilder builder() {
        return AttemptedPasscodes.builder()
                .timestamp(Instant.parse("2021-02-23T05:19:59.051Z"))
                .values(Arrays.asList("11111111", "22222222"));
    }

}
