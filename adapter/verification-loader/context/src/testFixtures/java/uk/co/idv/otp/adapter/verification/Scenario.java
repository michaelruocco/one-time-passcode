package uk.co.idv.otp.adapter.verification;

import uk.co.idv.method.entities.verification.Verification;

import java.util.UUID;
import java.util.function.Function;

public interface Scenario extends Function<UUID, Verification> {

    boolean shouldExecute(UUID contextId);

}
