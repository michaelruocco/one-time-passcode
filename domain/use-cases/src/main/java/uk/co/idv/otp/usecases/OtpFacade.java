package uk.co.idv.otp.usecases;

import lombok.Builder;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.send.ResendOtpRequest;
import uk.co.idv.otp.entities.send.SendOtpRequest;
import uk.co.idv.otp.usecases.send.GetOtp;
import uk.co.idv.otp.usecases.send.ResendOtp;
import uk.co.idv.otp.usecases.send.SendOtp;

import java.util.UUID;

@Builder
public class OtpFacade {

    private final SendOtp sendOtp;
    private final GetOtp getOtp;
    private final ResendOtp resendOtp;

    public OtpVerification send(SendOtpRequest request) {
        return sendOtp.send(request);
    }

    public OtpVerification getOtp(UUID id) {
        return getOtp.get(id);
    }

    public OtpVerification resend(ResendOtpRequest request) {
        return resendOtp.resend(request);
    }

}
