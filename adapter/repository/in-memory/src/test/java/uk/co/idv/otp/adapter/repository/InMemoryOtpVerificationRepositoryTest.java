package uk.co.idv.otp.adapter.repository;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.OtpVerificationMother;
import uk.co.idv.otp.usecases.OtpVerificationRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryOtpVerificationRepositoryTest {

    private final OtpVerificationRepository repository = new InMemoryOtpVerificationRepository();

    @Test
    void shouldReturnEmptyOptionalIfVerificationNotFoundById() {
        UUID id = UUID.fromString("50afc389-ede8-4820-8498-f1ac32aeeb45");

        Optional<OtpVerification> verification = repository.load(id);

        assertThat(verification).isEmpty();
    }

    @Test
    void shouldLoadVerificationById() {
        OtpVerification verification = OtpVerificationMother.build();
        repository.save(verification);

        Optional<OtpVerification> loaded = repository.load(verification.getId());

        assertThat(loaded).contains(verification);
    }

}
