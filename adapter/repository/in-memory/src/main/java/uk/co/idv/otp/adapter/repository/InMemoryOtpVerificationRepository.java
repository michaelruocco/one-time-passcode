package uk.co.idv.otp.adapter.repository;

import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.usecases.VerificationNotFoundException;
import uk.co.idv.otp.usecases.OtpVerificationRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryOtpVerificationRepository implements OtpVerificationRepository {

    private final Map<UUID, OtpVerification> verifications = new HashMap<>();

    @Override
    public void save(OtpVerification verification) {
        verifications.put(verification.getId(), verification);
    }

    @Override
    public OtpVerification load(UUID id) {
        return Optional.ofNullable(verifications.get(id))
                .orElseThrow(() -> new VerificationNotFoundException(id));
    }

}
