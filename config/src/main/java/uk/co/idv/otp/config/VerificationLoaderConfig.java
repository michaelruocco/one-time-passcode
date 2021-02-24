package uk.co.idv.otp.config;

import uk.co.idv.otp.usecases.send.OtpVerificationLoader;
import uk.co.idv.otp.usecases.verify.OtpVerificationUpdater;

public interface VerificationLoaderConfig {

    OtpVerificationLoader verificationLoader();

    OtpVerificationUpdater verificationUpdater();

}
