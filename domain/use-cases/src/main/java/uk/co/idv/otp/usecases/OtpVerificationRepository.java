package uk.co.idv.otp.usecases;

import uk.co.idv.otp.entities.OtpVerification;

import java.util.Optional;
import java.util.UUID;

public interface OtpVerificationRepository {

    void save(OtpVerification verification);

    Optional<OtpVerification> load(UUID id);

}
