package uk.co.idv.otp.adapter.verification.loader;

import lombok.Builder;
import uk.co.idv.activity.entities.OnlinePurchaseMother;
import uk.co.idv.context.adapter.verification.client.VerificationClient;
import uk.co.idv.context.adapter.verification.client.header.DefaultIdvHeaderValidator;
import uk.co.idv.context.adapter.verification.client.header.IdvHeaderValidator;
import uk.co.idv.context.adapter.verification.client.stub.StubVerificationClient;
import uk.co.idv.context.adapter.verification.client.stub.complete.CompleteVerificationScenario;
import uk.co.idv.context.adapter.verification.client.stub.complete.CompleteVerificationSuccessScenario;
import uk.co.idv.context.adapter.verification.client.stub.create.CreateVerificationExpiredScenario;
import uk.co.idv.context.adapter.verification.client.stub.create.CreateVerificationNotFoundScenario;
import uk.co.idv.context.adapter.verification.client.stub.create.CreateVerificationNotNextMethodScenario;
import uk.co.idv.context.adapter.verification.client.stub.create.CreateVerificationScenario;
import uk.co.idv.context.adapter.verification.client.stub.create.CreateVerificationSuccessScenario;
import uk.co.idv.context.adapter.verification.client.stub.create.VerificationFactory;
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
                .defaultCompleteScenario(defaultCompleteScenario())
                .build();
    }

    private CreateVerificationScenario defaultCreateScenario() {
        return CreateVerificationSuccessScenario.builder()
                .verificationFactory(stubVerificationFactory())
                .build();
    }

    private VerificationFactory stubVerificationFactory() {
        Otp otp = OtpMother.withDeliveryMethod(DeliveryMethodMother.eligible());
        return VerificationFactory.builder()
                .clock(clock)
                .methods(MethodsMother.with(otp))
                .activity(OnlinePurchaseMother.build())
                .protectSensitiveData(true)
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

    private static CompleteVerificationScenario defaultCompleteScenario() {
        return CompleteVerificationSuccessScenario.builder().build();
    }

    public static class OtpStubVerificationClientFactoryBuilder {

        public VerificationClient buildClient() {
            return build().build();
        }

    }

}
