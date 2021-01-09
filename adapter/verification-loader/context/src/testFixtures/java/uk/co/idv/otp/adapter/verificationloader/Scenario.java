package uk.co.idv.otp.adapter.verificationloader;

import uk.co.idv.context.entities.verification.Verification;

import java.util.UUID;
import java.util.function.Function;

public interface Scenario extends Function<UUID, Verification> {

    boolean shouldExecute(UUID contextId);

}
