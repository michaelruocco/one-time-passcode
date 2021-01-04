package uk.co.idv.otp.adapter.repository;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.OtpVerificationMother;
import uk.co.idv.otp.usecases.VerificationNotFoundException;
import uk.co.idv.otp.usecases.OtpVerificationRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

class InMemoryOtpVerificationRepositoryTest {

    private final OtpVerificationRepository repository = new InMemoryOtpVerificationRepository();

    @Test
    void shouldThrowExceptionIfVerificationNotFoundById() {
        UUID id = UUID.fromString("50afc389-ede8-4820-8498-f1ac32aeeb45");

        Throwable error = catchThrowable(() -> repository.load(id));

        assertThat(error)
                .isInstanceOf(VerificationNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldLoadVerificationById() {
        OtpVerification verification = OtpVerificationMother.build();
        repository.save(verification);

        OtpVerification loaded = repository.load(verification.getId());

        assertThat(loaded).isEqualTo(verification);
    }

}
