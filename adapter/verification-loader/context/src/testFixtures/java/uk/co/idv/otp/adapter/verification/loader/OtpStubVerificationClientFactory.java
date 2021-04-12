package uk.co.idv.otp.adapter.verification.loader;

import lombok.Builder;
import uk.co.idv.activity.entities.OnlinePurchaseMother;
import uk.co.idv.context.adapter.verification.client.VerificationClient;
import uk.co.idv.context.adapter.verification.client.header.DefaultIdvHeaderValidator;
import uk.co.idv.context.adapter.verification.client.header.IdvHeaderValidator;
import uk.co.idv.context.adapter.verification.client.stub.StubVerificationClient;
import uk.co.idv.context.adapter.verification.client.stub.create.CreateVerificationExpiredScenario;
import uk.co.idv.context.adapter.verification.client.stub.create.CreateVerificationNotFoundScenario;
import uk.co.idv.context.adapter.verification.client.stub.create.CreateVerificationNotNextMethodScenario;
import uk.co.idv.context.adapter.verification.client.stub.create.CreateVerificationScenario;
import uk.co.idv.context.adapter.verification.client.stub.create.CreateVerificationSuccessScenario;
import uk.co.idv.method.entities.method.MethodsMother;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.OtpMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;

import java.time.Clock;
import java.util.Arrays;
import java.util.Collection;

@Builder
public class OtpStubVerificationClientFactory {

    private final Clock clock;

    @Builder.Default
    private final IdvHeaderValidator headerValidator = new DefaultIdvHeaderValidator();

    public VerificationClient build() {
        return StubVerificationClient.builder()
                .headerValidator(headerValidator)
                .defaultCreateScenario(defaultCreateScenario())
                .createScenarios(buildSpecificCreateScenarios())
                .build();
    }

    private CreateVerificationScenario defaultCreateScenario() {
        Otp otp = OtpMother.withDeliveryMethod(DeliveryMethodMother.eligible());
        return CreateVerificationSuccessScenario.builder()
                .clock(clock)
                .methods(MethodsMother.with(otp))
                .activity(OnlinePurchaseMother.build())
                .build();
    }

    private static Collection<CreateVerificationScenario> buildSpecificCreateScenarios() {
        return Arrays.asList(
                new CreateVerificationNotFoundScenario(),
                new CreateVerificationExpiredScenario(),
                new CreateVerificationNotNextMethodScenario(),
                new OtpMethodNotFoundScenario(),
                new DeliveryMethodNotFoundScenario(),
                new DeliveryMethodNotEligibleScenario()
        );
    }

    public static class OtpStubVerificationClientFactoryBuilder {

        public VerificationClient buildClient() {
            return build().build();
        }

    }

}