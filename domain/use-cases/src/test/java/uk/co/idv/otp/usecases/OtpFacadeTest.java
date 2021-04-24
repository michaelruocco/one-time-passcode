package uk.co.idv.otp.usecases;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.OtpVerificationMother;
import uk.co.idv.otp.entities.send.ResendOtpRequest;
import uk.co.idv.otp.entities.send.ResendOtpRequestMother;
import uk.co.idv.otp.entities.send.SendOtpRequest;
import uk.co.idv.otp.entities.send.SendOtpRequestMother;
import uk.co.idv.otp.entities.verify.VerifyOtpRequest;
import uk.co.idv.otp.entities.verify.VerifyOtpRequestMother;
import uk.co.idv.otp.usecases.get.GetOtp;
import uk.co.idv.otp.usecases.send.ResendOtp;
import uk.co.idv.otp.usecases.send.SendOtp;
import uk.co.idv.otp.usecases.verify.VerifyOtp;

import java.util.UUID;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OtpFacadeTest {

    private final SendOtp sendOtp = mock(SendOtp.class);
    private final GetOtp getOtp = mock(GetOtp.class);
    private final ResendOtp resendOtp = mock(ResendOtp.class);
    private final VerifyOtp verifyOtp = mock(VerifyOtp.class);
    private final UnaryOperator<OtpVerification> protector = mock(UnaryOperator.class);

    private final OtpFacade facade = OtpFacade.builder()
            .sendOtp(sendOtp)
            .getOtp(getOtp)
            .resendOtp(resendOtp)
            .verifyOtp(verifyOtp)
            .protector(protector)
            .build();

    @Test
    void shouldSendOtp() {
        SendOtpRequest request = SendOtpRequestMother.build();
        OtpVerification expectedVerification = OtpVerificationMother.incomplete();
        given(sendOtp.send(request)).willReturn(expectedVerification);

        OtpVerification verification = facade.send(request);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldProtectSensitiveDataOnSendOtpResponseIfRequired() {
        SendOtpRequest request = SendOtpRequestMother.build();
        OtpVerification originalVerification = givenVerificationWithProtectSensitiveData();
        given(sendOtp.send(request)).willReturn(originalVerification);
        OtpVerification expectedVerification = givenProtectedVerification(originalVerification);

        OtpVerification verification = facade.send(request);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldGetOtp() {
        OtpVerification expectedVerification = OtpVerificationMother.incomplete();
        UUID id = expectedVerification.getId();
        given(getOtp.get(id)).willReturn(expectedVerification);

        OtpVerification verification = facade.getOtp(id);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldProtectSensitiveDataOnGetOtpIfRequired() {
        OtpVerification originalVerification = givenVerificationWithProtectSensitiveData();
        UUID id = originalVerification.getId();
        given(getOtp.get(id)).willReturn(originalVerification);
        OtpVerification expectedVerification = givenProtectedVerification(originalVerification);

        OtpVerification verification = facade.getOtp(id);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldResendOtp() {
        ResendOtpRequest request = ResendOtpRequestMother.build();
        OtpVerification expectedVerification = OtpVerificationMother.incomplete();
        given(resendOtp.resend(request)).willReturn(expectedVerification);

        OtpVerification verification = facade.resend(request);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldProtectSensitiveDataOnResendOtpIfRequired() {
        ResendOtpRequest request = ResendOtpRequestMother.build();
        OtpVerification originalVerification = givenVerificationWithProtectSensitiveData();
        given(resendOtp.resend(request)).willReturn(originalVerification);
        OtpVerification expectedVerification = givenProtectedVerification(originalVerification);

        OtpVerification verification = facade.resend(request);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldVerifyOtp() {
        VerifyOtpRequest request = VerifyOtpRequestMother.build();
        OtpVerification expectedVerification = OtpVerificationMother.incomplete();
        given(verifyOtp.verify(request)).willReturn(expectedVerification);

        OtpVerification verification = facade.verify(request);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldProtectSensitiveDataOnVerifyOtpIfRequired() {
        VerifyOtpRequest request = VerifyOtpRequestMother.build();
        OtpVerification originalVerification = givenVerificationWithProtectSensitiveData();
        given(verifyOtp.verify(request)).willReturn(originalVerification);
        OtpVerification expectedVerification = givenProtectedVerification(originalVerification);

        OtpVerification verification = facade.verify(request);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    private OtpVerification givenProtectedVerification(OtpVerification verification) {
        OtpVerification protectedVerification = mock(OtpVerification.class);
        given(protector.apply(verification)).willReturn(protectedVerification);
        return protectedVerification;
    }

    private static OtpVerification givenVerificationWithProtectSensitiveData() {
        return OtpVerificationMother.builder()
                .protectSensitiveData(true)
                .build();
    }

}
