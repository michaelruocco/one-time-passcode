package uk.co.idv.otp.usecases;

import lombok.Builder;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.send.ResendOtpRequest;
import uk.co.idv.otp.entities.send.SendOtpRequest;
import uk.co.idv.otp.entities.verify.VerifyOtpRequest;
import uk.co.idv.otp.usecases.get.GetOtp;
import uk.co.idv.otp.usecases.send.ResendOtp;
import uk.co.idv.otp.usecases.send.SendOtp;
import uk.co.idv.otp.usecases.verify.VerifyOtp;

import java.util.UUID;
import java.util.function.UnaryOperator;

@Builder
public class OtpFacade {

    private final SendOtp sendOtp;
    private final GetOtp getOtp;
    private final ResendOtp resendOtp;
    private final VerifyOtp verifyOtp;
    private final UnaryOperator<OtpVerification> protector;

    public OtpVerification send(SendOtpRequest request) {
        return protectIfRequired(sendOtp.send(request));
    }

    public OtpVerification getOtp(UUID id) {
        return protectIfRequired(getOtp.get(id));
    }

    public OtpVerification resend(ResendOtpRequest request) {
        return protectIfRequired(resendOtp.resend(request));
    }

    public OtpVerification verify(VerifyOtpRequest request) {
        return protectIfRequired(verifyOtp.verify(request));
    }

    private OtpVerification protectIfRequired(OtpVerification verification) {
        if (verification.isProtectSensitiveData()) {
            return protector.apply(verification);
        }
        return verification;
    }

}
