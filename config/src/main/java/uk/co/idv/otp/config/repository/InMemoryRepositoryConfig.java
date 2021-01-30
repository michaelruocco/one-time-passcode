package uk.co.idv.otp.config.repository;

import uk.co.idv.otp.adapter.repository.InMemoryOtpVerificationRepository;
import uk.co.idv.otp.config.RepositoryConfig;
import uk.co.idv.otp.usecases.OtpVerificationRepository;

public class InMemoryRepositoryConfig implements RepositoryConfig {

    @Override
    public OtpVerificationRepository verificationRepository() {
        return new InMemoryOtpVerificationRepository();
    }

}
