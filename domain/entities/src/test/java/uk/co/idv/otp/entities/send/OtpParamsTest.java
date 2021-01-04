package uk.co.idv.otp.entities.send;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.otp.OtpConfig;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class OtpParamsTest {

    @Test
    void shouldReturnDeliveryMethod() {
        DeliveryMethod method = mock(DeliveryMethod.class);

        OtpParams request = OtpParams.builder()
                .deliveryMethod(method)
                .build();

        assertThat(request.getDeliveryMethod()).isEqualTo(method);
    }

    @Test
    void shouldReturnOtpConfig() {
        OtpConfig otpConfig = mock(OtpConfig.class);

        OtpParams request = OtpParams.builder()
                .otpConfig(otpConfig)
                .build();

        assertThat(request.getOtpConfig()).isEqualTo(otpConfig);
    }

}
