package uk.co.idv.otp.adapter.repository;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.Verification;
import uk.co.idv.otp.entities.VerificationMother;
import uk.co.idv.otp.usecases.VerificationNotFoundException;
import uk.co.idv.otp.usecases.VerificationRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

class InMemoryVerificationRepositoryTest {

    private final VerificationRepository repository = new InMemoryVerificationRepository();

    @Test
    void shouldThrowExceptionIfVerificationNotFoundById() {
        UUID id = UUID.fromString("50afc389-ede8-4820-8498-f1ac32aeeb45");

        Throwable error = catchThrowable(() -> repository.load(id));

        assertThat(error)
                .isInstanceOf(VerificationNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldThrowExceptionIfVerificationNotFoundByContextId() {
        UUID contextId = UUID.fromString("6e6c45f9-6960-46f8-9486-9218bb5cde2a");

        Throwable error = catchThrowable(() -> repository.loadByContextId(contextId));

        assertThat(error)
                .isInstanceOf(VerificationNotFoundException.class)
                .hasMessage(contextId.toString());
    }

    @Test
    void shouldLoadVerificationById() {
        Verification verification = VerificationMother.build();
        repository.save(verification);

        Verification loaded = repository.load(verification.getId());

        assertThat(loaded).isEqualTo(verification);
    }

    @Test
    void shouldLoadVerificationByContextId() {
        Verification verification = VerificationMother.build();
        repository.save(verification);

        Verification loaded = repository.loadByContextId(verification.getContextId());

        assertThat(loaded).isEqualTo(verification);
    }

}
