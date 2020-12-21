package uk.co.idv.otp.entities.verification;

import java.time.Instant;
import java.util.UUID;

public interface VerificationMother {

    static Verification build() {
        return builder().build();
    }

    static Verification.VerificationBuilder builder() {
        return Verification.builder()
                .id(UUID.fromString("624c63c6-a967-4b45-ab19-0237e1617122"))
                .contextId(UUID.fromString("2948aadc-7f63-4b00-875b-77a4e6608e5c"))
                .created(Instant.parse("2020-09-14T20:03:01.999Z"))
                .expiry(Instant.parse("2020-09-14T20:08:01.999Z"))
                .deliveries(DeliveriesMother.one())
                .successful(false)
                .complete(false);
    }

}
