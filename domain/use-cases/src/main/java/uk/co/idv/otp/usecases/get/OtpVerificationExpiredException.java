package uk.co.idv.otp.usecases.get;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class OtpVerificationExpiredException extends RuntimeException {

    private final UUID id;
    private final Instant expiry;

    public OtpVerificationExpiredException(UUID id, Instant expiry) {
        super(toMessage(id, expiry));
        this.id = id;
        this.expiry = expiry;
    }

    private static String toMessage(UUID id, Instant expiry) {
        return String.format("verification %s expired at %s", id, expiry);
    }

}
