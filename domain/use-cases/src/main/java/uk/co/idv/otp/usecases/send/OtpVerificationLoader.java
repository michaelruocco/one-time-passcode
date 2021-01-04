package uk.co.idv.otp.usecases.send;

import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.send.LoadOtpVerificationRequest;

public interface OtpVerificationLoader {

    OtpVerification load(LoadOtpVerificationRequest request);

}
