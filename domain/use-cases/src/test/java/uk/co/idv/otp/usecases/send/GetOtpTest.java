package uk.co.idv.otp.usecases.send;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.OtpVerificationMother;
import uk.co.idv.otp.usecases.OtpVerificationRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetOtpTest {

    private final OtpVerificationRepository repository = mock(OtpVerificationRepository.class);

    private final GetOtp getOtp = new GetOtp(repository);

    @Test
    void shouldReturnOtpVerification() {
        OtpVerification expectedVerification = OtpVerificationMother.build();
        UUID id = expectedVerification.getId();
        given(repository.load(id)).willReturn(expectedVerification);

        OtpVerification verification = getOtp.get(id);

        assertThat(verification).isEqualTo(expectedVerification);
    }

}
