package uk.co.idv.otp.config;

import uk.co.idv.otp.usecases.OtpVerificationRepository;

public interface RepositoryConfig {

    OtpVerificationRepository verificationRepository();

}
