package uk.co.idv.otp.adapter.verificationloader;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.context.entities.verification.VerificationMother;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.OtpMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.method.entities.otp.delivery.query.DeliveryMethodNotFoundException;
import uk.co.idv.otp.entities.send.OtpParams;
import uk.co.idv.otp.usecases.send.DeliveryMethodNotEligibleException;
import uk.co.idv.otp.usecases.send.OtpNotNextEligibleMethodException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class OtpParamsExtractorTest {

    private final OtpParamsExtractor extractor = new OtpParamsExtractor();

    @Test
    void shouldThrowExceptionMethodsAreEmpty() {
        Verification verification = VerificationMother.withoutMethods();
        UUID deliveryMethodId = UUID.randomUUID();

        Throwable error = catchThrowable(() -> extractor.extract(verification, deliveryMethodId));

        assertThat(error)
                .isInstanceOf(OtpNotNextEligibleMethodException.class)
                .hasMessage(verification.getContextId().toString());
    }

    @Test
    void shouldThrowExceptionIfDeliveryMethodWithIdIsNotPresent() {
        Verification verification = VerificationMother.withMethod(OtpMother.withEmptyDeliveryMethods());
        UUID deliveryMethodId = UUID.randomUUID();

        Throwable error = catchThrowable(() -> extractor.extract(verification, deliveryMethodId));

        assertThat(error)
                .isInstanceOf(DeliveryMethodNotFoundException.class)
                .hasMessage(deliveryMethodId.toString());
    }

    @Test
    void shouldThrowExceptionIfDeliveryMethodWithIdIsPresentButNotEligible() {
        DeliveryMethod deliveryMethod = DeliveryMethodMother.ineligible();
        Verification verification = VerificationMother.withMethod(OtpMother.withDeliveryMethods(deliveryMethod));

        Throwable error = catchThrowable(() -> extractor.extract(verification, deliveryMethod.getId()));

        assertThat(error)
                .isInstanceOf(DeliveryMethodNotEligibleException.class)
                .hasMessage(deliveryMethod.getId().toString());
    }

    @Test
    void shouldReturnOtpParamsAndDeliveryMethodIfDeliveryMethodIsEligible() {
        DeliveryMethod deliveryMethod = DeliveryMethodMother.eligible();
        Otp otp = OtpMother.withDeliveryMethods(deliveryMethod);
        Verification verification = VerificationMother.withMethod(otp);

        OtpParams params = extractor.extract(verification, deliveryMethod.getId());

        assertThat(params.getOtpConfig()).isEqualTo(otp.getConfig());
        assertThat(params.getDeliveryMethod()).isEqualTo(deliveryMethod);
    }

}
