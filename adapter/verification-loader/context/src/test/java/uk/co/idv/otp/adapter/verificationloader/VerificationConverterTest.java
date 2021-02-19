package uk.co.idv.otp.adapter.verificationloader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.delivery.Deliveries;
import uk.co.idv.otp.entities.send.LoadOtpVerificationRequest;
import uk.co.idv.otp.entities.send.OtpParams;
import uk.co.idv.otp.entities.send.OtpParamsMother;
import uk.co.idv.otp.entities.send.SendOtpRequestMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationConverterTest {

    private final OtpParamsExtractor paramsExtractor = mock(OtpParamsExtractor.class);

    private final VerificationConverter converter = new VerificationConverter(paramsExtractor);

    private final Verification verification = VerificationMother.successful();
    private final LoadOtpVerificationRequest request = SendOtpRequestMother.build();
    private final OtpParams params = OtpParamsMother.build();

    @BeforeEach
    void setUp() {
        given(paramsExtractor.extract(verification, request.getDeliveryMethodId())).willReturn(params);
    }
    @Test
    void shouldPopulateEmptyDeliveries() {
        OtpVerification otpVerification = converter.toOtpVerification(request, verification);

        Deliveries deliveries = otpVerification.getDeliveries();
        assertThat(deliveries).isEmpty();
        assertThat(deliveries.getMax()).isEqualTo(params.getMaxNumberOfPasscodeDeliveries());
    }

    @Test
    void shouldPopulateOtpParams() {
        OtpVerification otpVerification = converter.toOtpVerification(request, verification);

        assertThat(otpVerification.getConfig()).isEqualTo(params.getOtpConfig());
    }

    @Test
    void shouldPopulateDeliveryMethod() {
        OtpVerification otpVerification = converter.toOtpVerification(request, verification);

        assertThat(otpVerification.getDeliveryMethod()).isEqualTo(params.getDeliveryMethod());
    }

    @Test
    void shouldPopulateId() {
        OtpVerification otpVerification = converter.toOtpVerification(request, verification);

        assertThat(otpVerification.getId()).isEqualTo(verification.getId());
    }

    @Test
    void shouldPopulateContextId() {
        OtpVerification otpVerification = converter.toOtpVerification(request, verification);

        assertThat(otpVerification.getContextId()).isEqualTo(verification.getContextId());
    }

    @Test
    void shouldPopulateCreated() {
        OtpVerification otpVerification = converter.toOtpVerification(request, verification);

        assertThat(otpVerification.getCreated()).isEqualTo(verification.getCreated());
    }

    @Test
    void shouldPopulateExpiry() {
        OtpVerification otpVerification = converter.toOtpVerification(request, verification);

        assertThat(otpVerification.getExpiry()).isEqualTo(verification.getExpiry());
    }

    @Test
    void shouldPopulateActivity() {
        OtpVerification otpVerification = converter.toOtpVerification(request, verification);

        assertThat(otpVerification.getActivity()).isEqualTo(verification.getActivity());
    }

    @Test
    void shouldPopulateProtectSensitiveData() {
        OtpVerification otpVerification = converter.toOtpVerification(request, verification);

        assertThat(otpVerification.isProtectSensitiveData()).isEqualTo(verification.isProtectSensitiveData());
    }

}
