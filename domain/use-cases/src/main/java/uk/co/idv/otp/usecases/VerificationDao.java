package uk.co.idv.otp.usecases;

import uk.co.idv.otp.entities.verification.Verification;

import java.util.UUID;

public interface VerificationDao {

    void save(Verification verification);

    Verification load(UUID id);

    Verification loadByContextId(UUID contextId);
}
