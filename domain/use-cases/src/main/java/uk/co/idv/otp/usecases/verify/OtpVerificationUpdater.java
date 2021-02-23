package uk.co.idv.otp.usecases.verify;

import uk.co.idv.otp.entities.OtpVerification;

public interface OtpVerificationUpdater {

    OtpVerification update(OtpVerification verification);

}
