package uk.co.idv.otp.adapter.repository;

import uk.co.idv.otp.entities.Verification;
import uk.co.idv.otp.usecases.VerificationNotFoundException;
import uk.co.idv.otp.usecases.VerificationRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryVerificationRepository implements VerificationRepository {

    private final Map<UUID, Verification> verifications = new HashMap<>();

    @Override
    public void save(Verification verification) {
        verifications.put(verification.getId(), verification);
    }

    @Override
    public Verification load(UUID id) {
        return Optional.ofNullable(verifications.get(id))
                .orElseThrow(() -> new VerificationNotFoundException(id));
    }

    @Override
    public Verification loadByContextId(UUID contextId) {
        return verifications.values().stream()
                .filter(verification -> verification.getContextId().equals(contextId))
                .findFirst()
                .orElseThrow(() -> new VerificationNotFoundException(contextId));
    }

}
