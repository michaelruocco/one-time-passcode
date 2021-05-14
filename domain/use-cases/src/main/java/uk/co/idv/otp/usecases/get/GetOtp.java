package uk.co.idv.otp.usecases.get;

import lombok.Builder;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.usecases.OtpVerificationRepository;

import java.time.Clock;
import java.util.UUID;

@Builder
public class GetOtp {

    private final Clock clock;
    private final OtpVerificationRepository repository;

    public OtpVerification getIfIncomplete(UUID id) {
        OtpVerification verification = get(id);
        if (verification.isComplete()) {
            throw new OtpVerificationAlreadyCompleteException(id);
        }
        return verification;
    }

    public OtpVerification get(UUID id) {
        OtpVerification verification = repository.load(id).orElseThrow(() -> new OtpVerificationNotFoundException(id));
        if (verification.hasExpired(clock.instant())) {
            throw new OtpVerificationExpiredException(id, verification.getExpiry());
        }
        return verification;
    }

}
