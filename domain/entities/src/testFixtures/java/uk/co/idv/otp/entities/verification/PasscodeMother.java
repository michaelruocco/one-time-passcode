package uk.co.idv.otp.entities.verification;

import uk.co.idv.otp.entities.passcode.Passcode;

import java.time.Instant;

public interface PasscodeMother {

    static Passcode build() {
        return builder().build();
    }

    static Passcode withValue(String value) {
        return builder().value(value).build();
    }

    static Passcode.PasscodeBuilder builder() {
        return Passcode.builder()
                .value("87654321")
                .created(Instant.parse("2020-09-14T20:04:02.002Z"))
                .expiry(Instant.parse("2020-09-14T20:09:02.002Z"));
    }

}
