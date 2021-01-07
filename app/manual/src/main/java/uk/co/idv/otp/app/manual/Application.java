package uk.co.idv.otp.app.manual;

import uk.co.idv.otp.config.OtpAppConfig;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.send.ResendOtpRequest;
import uk.co.idv.otp.entities.send.SendOtpRequest;
import uk.co.idv.otp.usecases.OtpFacade;

import java.util.UUID;

public class Application {

    private final OtpFacade facade;

    public Application(OtpAppConfig otpConfig) {
        this.facade = otpConfig.facade();
    }

    public OtpVerification sendOtp(SendOtpRequest request) {
        return facade.send(request);
    }

    public OtpVerification getOtp(UUID id) {
        return facade.getOtp(id);
    }

    public OtpVerification resendOtp(ResendOtpRequest request) {
        return facade.resend(request);
    }

}
