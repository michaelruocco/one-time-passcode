package uk.co.idv.otp.adapter.verification;

import lombok.RequiredArgsConstructor;
import uk.co.idv.otp.adapter.verification.loader.ContextNotFoundScenario;
import uk.co.idv.otp.adapter.verification.loader.DeliveryMethodNotEligibleScenario;
import uk.co.idv.otp.adapter.verification.loader.DeliveryMethodNotFoundScenario;
import uk.co.idv.otp.adapter.verification.loader.OtpMethodNotFoundScenario;
import uk.co.idv.otp.adapter.verification.loader.ValidCreateScenario;

import java.time.Clock;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
public class Scenarios {

    private final Scenario defaultScenario;
    private final Collection<Scenario> specificScenarios;

    public Scenarios(Clock clock) {
        this(new ValidCreateScenario(clock), buildSpecificScenarios());
    }

    public Scenario find(UUID id) {
        return specificScenarios.stream()
                .filter(scenario -> scenario.shouldExecute(id))
                .findFirst()
                .orElse(defaultScenario);
    }

    private static Collection<Scenario> buildSpecificScenarios() {
        return Arrays.asList(
                new ContextExpiredScenario(),
                new ContextNotFoundScenario(),
                new OtpMethodNotFoundScenario(),
                new DeliveryMethodNotFoundScenario(),
                new DeliveryMethodNotEligibleScenario()
        );
    }

}
