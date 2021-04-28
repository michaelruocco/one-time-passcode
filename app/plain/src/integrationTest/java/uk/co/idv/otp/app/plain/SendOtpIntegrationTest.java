package uk.co.idv.otp.app.plain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.idv.activity.entities.OnlinePurchaseMother;
import uk.co.idv.context.adapter.verification.client.exception.ApiErrorClientException;
import uk.co.idv.method.adapter.json.error.contextexpired.ContextExpiredError;
import uk.co.idv.method.adapter.json.error.contextnotfound.ContextNotFoundError;
import uk.co.idv.method.entities.otp.OtpConfigMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.method.entities.otp.delivery.query.DeliveryMethodNotFoundException;
import uk.co.idv.otp.adapter.verification.loader.DeliveryMethodNotEligibleScenario;
import uk.co.idv.otp.adapter.verification.loader.DeliveryMethodNotFoundScenario;
import uk.co.idv.otp.adapter.verification.loader.OtpMethodNotFoundScenario;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.delivery.Deliveries;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryMother;
import uk.co.idv.otp.entities.delivery.NoDeliveriesRemainingException;
import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.entities.passcode.PasscodeMother;
import uk.co.idv.otp.entities.send.ResendOtpRequest;
import uk.co.idv.otp.entities.send.SendOtpRequest;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.idv.otp.entities.send.message.MessageMother;
import uk.co.idv.otp.usecases.send.DeliveryMethodNotEligibleException;
import uk.co.idv.otp.usecases.send.OtpNotNextEligibleMethodException;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class SendOtpIntegrationTest {

    private final TestHarness harness = new TestHarness();
    private final Application application = harness.getApplication();

    @BeforeEach
    void setUp() {
        harness.setupMdc();
    }

    @AfterEach
    void tearDown() {
        harness.clearMdc();
    }

    @Test
    void shouldThrowExceptionIfContextIsNotFound() {
        String notFoundId = "9ed739ec-a252-4a3f-840c-4e2bdccf56e6";
        SendOtpRequest request = SendOtpRequest.builder()
                .contextId(UUID.fromString(notFoundId))
                .deliveryMethodId(UUID.randomUUID())
                .build();

        ApiErrorClientException error = catchThrowableOfType(
                () -> application.sendOtp(request),
                ApiErrorClientException.class
        );

        assertThat(error.getError())
                .usingRecursiveComparison()
                .isEqualTo(new ContextNotFoundError(notFoundId));
    }

    @Test
    void shouldThrowExceptionIfContextIsExpired() {
        String expiredId = "2b1f8ba4-00e7-4ad9-819f-5249af834f2e";
        Instant expectedExpiry = Instant.parse("2021-01-04T23:24:07.385Z");
        SendOtpRequest request = SendOtpRequest.builder()
                .contextId(UUID.fromString(expiredId))
                .deliveryMethodId(UUID.randomUUID())
                .build();

        ApiErrorClientException error = catchThrowableOfType(
                () -> application.sendOtp(request),
                ApiErrorClientException.class
        );

        assertThat(error.getError())
                .usingRecursiveComparison()
                .isEqualTo(new ContextExpiredError(UUID.fromString(expiredId), expectedExpiry));
    }

    @Test
    void shouldThrowExceptionIfOtpNotNextEligibleMethod() {
        String id = OtpMethodNotFoundScenario.ID.toString();
        SendOtpRequest request = SendOtpRequest.builder()
                .contextId(UUID.fromString(id))
                .deliveryMethodId(UUID.randomUUID())
                .build();

        Throwable error = catchThrowable(() -> application.sendOtp(request));

        assertThat(error)
                .isInstanceOf(OtpNotNextEligibleMethodException.class)
                .hasMessage(id);
    }

    @Test
    void shouldThrowExceptionIfDeliveryMethodNotFound() {
        String id = DeliveryMethodNotFoundScenario.ID.toString();
        UUID deliveryMethodId = UUID.randomUUID();
        SendOtpRequest request = SendOtpRequest.builder()
                .contextId(UUID.fromString(id))
                .deliveryMethodId(deliveryMethodId)
                .build();

        Throwable error = catchThrowable(() -> application.sendOtp(request));

        assertThat(error)
                .isInstanceOf(DeliveryMethodNotFoundException.class)
                .hasMessage(deliveryMethodId.toString());
    }

    @Test
    void shouldThrowExceptionIfDeliveryMethodNotEligible() {
        String id = DeliveryMethodNotEligibleScenario.ID.toString();
        UUID deliveryMethodId = UUID.fromString("c9959188-969e-42f3-8178-42ef824c81d3");
        SendOtpRequest request = SendOtpRequest.builder()
                .contextId(UUID.fromString(id))
                .deliveryMethodId(deliveryMethodId)
                .build();

        Throwable error = catchThrowable(() -> application.sendOtp(request));

        assertThat(error)
                .isInstanceOf(DeliveryMethodNotEligibleException.class)
                .hasMessage(deliveryMethodId.toString());
    }

    @Test
    void shouldSendOtpDelivery() {
        SendOtpRequest request = harness.buildSuccessfulSendOtpRequest();

        application.sendOtp(request);

        assertThat(harness.getLastDelivery()).isEqualTo(firstExpectedDelivery());
    }

    @Test
    void shouldPopulateVerificationWithValuesFromContext() {
        SendOtpRequest request = harness.buildSuccessfulSendOtpRequest();

        OtpVerification created = application.sendOtp(request);

        assertThat(created.getContextId()).isEqualTo(request.getContextId());
        assertThat(created.getCreated()).isEqualTo(harness.now());
        assertThat(created.getExpiry()).isEqualTo(harness.now().plus(Duration.ofMinutes(5)));
        assertThat(created.getActivity()).isEqualTo(OnlinePurchaseMother.build());
        assertThat(created.getDeliveryMethod()).isEqualTo(maskedPhoneNumber());
        assertThat(created.getConfig()).isEqualTo(OtpConfigMother.build());
        assertThat(created.isProtectSensitiveData()).isTrue();
    }

    @Test
    void shouldPopulateOtpValuesOnVerification() {
        SendOtpRequest request = harness.buildSuccessfulSendOtpRequest();

        OtpVerification created = application.sendOtp(request);

        assertThat(created.getId()).isEqualTo(UUID.fromString("81e11840-140e-45ac-a6af-40aa653a146e"));
        assertThat(created.isSuccessful()).isFalse();
        assertThat(created.isComplete()).isFalse();
        Deliveries deliveries = created.getDeliveries();
        assertThat(deliveries.getMax()).isEqualTo(2);
        assertThat(deliveries.getValues()).containsExactly(firstExpectedDelivery(maskedPhoneNumber()));
    }

    @Test
    void shouldSaveVerification() {
        SendOtpRequest request = harness.buildSuccessfulSendOtpRequest();
        OtpVerification created = application.sendOtp(request);

        OtpVerification retrieved = application.getOtp(created.getId());

        assertThat(retrieved).isEqualTo(created);
    }

    @Test
    void shouldResendPasscode() {
        SendOtpRequest sendRequest = harness.buildSuccessfulSendOtpRequest();
        OtpVerification initial = application.sendOtp(sendRequest);
        ResendOtpRequest resendRequest = new ResendOtpRequest(initial.getId());

        OtpVerification updated = application.resendOtp(resendRequest);

        assertThat(updated)
                .usingRecursiveComparison()
                .ignoringFields("deliveries")
                .isEqualTo(initial);
        assertThat(updated.getDeliveries()).containsExactly(
                firstExpectedDelivery(maskedPhoneNumber()),
                secondExpectedDelivery(maskedPhoneNumber())
        );
    }

    @Test
    void shouldThrowExceptionIfAttemptToResendAfterMaxNumberOfDeliveriesAlreadyPerformed() {
        SendOtpRequest sendRequest = harness.buildSuccessfulSendOtpRequest();
        OtpVerification initial = application.sendOtp(sendRequest);
        ResendOtpRequest resendRequest = new ResendOtpRequest(initial.getId());
        application.resendOtp(resendRequest);

        Throwable error = catchThrowable(() -> application.resendOtp(resendRequest));

        assertThat(error)
                .isInstanceOf(NoDeliveriesRemainingException.class)
                .hasMessage("2");
    }

    private Delivery firstExpectedDelivery() {
        return firstExpectedDelivery(DeliveryMethodMother.eligible());
    }

    private Delivery firstExpectedDelivery(DeliveryMethod deliveryMethod) {
        return DeliveryMother.builder()
                .sent(harness.now())
                .method(deliveryMethod)
                .message(toExpectedMessage(toPasscode("00000001")))
                .messageId("76c9ec3b-b7aa-41ae-8066-796856e71e65")
                .build();
    }

    private Delivery secondExpectedDelivery(DeliveryMethod deliveryMethod) {
        return DeliveryMother.builder()
                .sent(harness.now())
                .method(deliveryMethod)
                .message(toExpectedMessage(toPasscode("00000002")))
                .messageId("85bbb05a-3cf8-45e5-bae8-430503164c3b")
                .build();
    }

    private Message toExpectedMessage(Passcode passcode) {
        return MessageMother.withPasscode(passcode);
    }

    private Passcode toPasscode(String value) {
        return PasscodeMother.builder()
                .created(harness.now())
                .expiry(harness.now().plus(Duration.ofMinutes(2)))
                .value(value)
                .build();
    }

    private static DeliveryMethod maskedPhoneNumber() {
        return DeliveryMethodMother.builder()
                .value("***********743")
                .build();
    }

}
