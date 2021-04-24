package uk.co.idv.otp.usecases.verify;

import lombok.Builder;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.verify.AttemptedPasscodes;
import uk.co.idv.otp.entities.verify.VerifyOtpRequest;
import uk.co.idv.otp.usecases.OtpVerificationRepository;
import uk.co.idv.otp.usecases.get.GetOtp;

import java.time.Clock;
import java.util.Collection;

@Builder
public class VerifyOtp {

    private final GetOtp getOtp;
    private final Clock clock;
    private final OtpVerificationUpdater updater;
    private final OtpVerificationRepository repository;

    public OtpVerification verify(VerifyOtpRequest request) {
        var verification = getOtp.get(request.getId());
        var attemptedPasscodes = toAttemptedPasscodes(request.getPasscodes());
        var verified = verification.verify(attemptedPasscodes);
        var updated = updater.update(verified);
        repository.save(updated);
        return updated;
    }

    private AttemptedPasscodes toAttemptedPasscodes(Collection<String> passcodes) {
        return AttemptedPasscodes.builder()
                .timestamp(clock.instant())
                .values(passcodes)
                .build();
    }

}
