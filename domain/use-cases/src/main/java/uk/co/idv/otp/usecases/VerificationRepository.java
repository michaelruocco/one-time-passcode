package uk.co.idv.otp.usecases;

import uk.co.idv.otp.entities.Verification;

import java.util.UUID;

public interface VerificationRepository {

    void save(Verification verification);

    Verification load(UUID id);

    Verification loadByContextId(UUID contextId);
}
