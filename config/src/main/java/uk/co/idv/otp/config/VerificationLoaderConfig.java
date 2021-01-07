package uk.co.idv.otp.config;

import uk.co.idv.otp.usecases.send.OtpVerificationLoader;

public interface VerificationLoaderConfig {

    OtpVerificationLoader verificationLoader();

}
