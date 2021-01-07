package uk.co.idv.otp.config;

import lombok.Builder;
import uk.co.idv.otp.usecases.OtpFacade;
import uk.co.idv.otp.usecases.OtpVerificationRepository;
import uk.co.idv.otp.usecases.passcode.PasscodeGenerator;
import uk.co.idv.otp.usecases.send.DeliverOtp;
import uk.co.idv.otp.usecases.send.GetOtp;
import uk.co.idv.otp.usecases.send.OtpVerificationLoader;
import uk.co.idv.otp.usecases.send.ResendOtp;
import uk.co.idv.otp.usecases.send.SendOtp;
import uk.co.idv.otp.usecases.send.message.CompositeMessageGenerator;
import uk.co.idv.otp.usecases.send.message.MessageGenerator;

import java.time.Clock;

@Builder
public class OtpAppConfig {

    private final Clock clock;
    private final OtpVerificationLoader verificationLoader;
    private final DeliverOtp deliverOtp;
    private final OtpVerificationRepository repository;
    private final PasscodeGenerator passcodeGenerator;

    public OtpFacade facade() {
        return OtpFacade.builder()
                .sendOtp(sendOtp())
                .getOtp(getOtp())
                .resendOtp(resendOtp())
                .build();
    }

    private SendOtp sendOtp() {
        return SendOtp.builder()
                .verificationLoader(verificationLoader)
                .passcodeGenerator(passcodeGenerator)
                .messageGenerator(messageGenerator())
                .deliverOtp(deliverOtp)
                .repository(repository)
                .build();
    }

    private GetOtp getOtp() {
        return new GetOtp(repository);
    }

    private ResendOtp resendOtp() {
        return ResendOtp.builder()
                .passcodeGenerator(passcodeGenerator)
                .messageGenerator(messageGenerator())
                .deliverOtp(deliverOtp)
                .repository(repository)
                .build();
    }

    private MessageGenerator messageGenerator() {
        return new CompositeMessageGenerator();
    }

}
