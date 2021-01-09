package uk.co.idv.otp.adapter.verificationloader;

import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
public class Scenarios {

    private final Scenario defaultScenario;
    private final Collection<Scenario> specificScenarios;

    public Scenarios(Clock clock) {
        this(new ValidScenario(clock), buildSpecificScenarios());
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
