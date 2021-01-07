package uk.co.idv.otp.usecases.send;

import lombok.RequiredArgsConstructor;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.usecases.OtpVerificationRepository;

import java.util.UUID;

@RequiredArgsConstructor
public class GetOtp {

    private final OtpVerificationRepository repository;

    public OtpVerification get(UUID id) {
        return repository.load(id);
    }

}
