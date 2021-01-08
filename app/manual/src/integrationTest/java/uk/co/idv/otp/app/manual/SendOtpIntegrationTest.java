package uk.co.idv.otp.app.manual;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.client.exception.ApiErrorClientException;
import uk.co.idv.context.adapter.json.error.contextexpired.ContextExpiredError;
import uk.co.idv.context.adapter.json.error.contextnotfound.ContextNotFoundError;
import uk.co.idv.context.entities.activity.OnlinePurchaseMother;
import uk.co.idv.method.entities.otp.OtpConfigMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.method.entities.otp.delivery.query.DeliveryMethodNotFoundException;
import uk.co.idv.otp.adapter.verificationloader.StubContextConstants;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.delivery.Deliveries;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryMother;
import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.entities.passcode.PasscodeMother;
import uk.co.idv.otp.entities.send.SendOtpRequest;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.idv.otp.entities.send.message.MessageMother;
import uk.co.idv.otp.usecases.send.DeliveryMethodNotEligibleException;
import uk.co.idv.otp.usecases.send.OtpNotNextEligibleMethodException;

import java.time.Duration;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static uk.co.idv.otp.adapter.verificationloader.StubContextConstants.CONTEXT_EXPIRY;

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
        String id = StubContextConstants.CONTEXT_NOT_FOUND_ID;
        SendOtpRequest request = SendOtpRequest.builder()
                .contextId(UUID.fromString(id))
                .deliveryMethodId(UUID.randomUUID())
                .build();

        ApiErrorClientException error = catchThrowableOfType(
                () -> application.sendOtp(request),
                ApiErrorClientException.class
        );

        assertThat(error.getError())
                .usingRecursiveComparison()
                .isEqualTo(new ContextNotFoundError(id));
    }

    @Test
    void shouldThrowExceptionIfContextIsExpiry() {
        String id = StubContextConstants.CONTEXT_EXPIRED_ID;
        SendOtpRequest request = SendOtpRequest.builder()
                .contextId(UUID.fromString(id))
                .deliveryMethodId(UUID.randomUUID())
                .build();

        ApiErrorClientException error = catchThrowableOfType(
                () -> application.sendOtp(request),
                ApiErrorClientException.class
        );

        assertThat(error.getError())
                .usingRecursiveComparison()
                .isEqualTo(new ContextExpiredError(UUID.fromString(id), CONTEXT_EXPIRY));
    }

    @Test
    void shouldThrowExceptionIfOtpNotNextEligibleMethod() {
        String id = StubContextConstants.OTP_METHOD_NOT_FOUND_ID;
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
        String id = StubContextConstants.DELIVERY_METHOD_NOT_FOUND_ID;
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
        String id = StubContextConstants.DELIVERY_METHOD_NOT_ELIGIBLE_ID;
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
        SendOtpRequest request = buildSuccessfulSendOtpRequest();

        application.sendOtp(request);

        assertThat(harness.getLastDelivery()).isEqualTo(expectedDelivery());
    }

    @Test
    void shouldPopulateVerificationWithValuesFromContext() {
        SendOtpRequest request = buildSuccessfulSendOtpRequest();

        OtpVerification created = application.sendOtp(request);

        assertThat(created.getContextId()).isEqualTo(request.getContextId());
        assertThat(created.getCreated()).isEqualTo(harness.now());
        assertThat(created.getExpiry()).isEqualTo(harness.now().plus(Duration.ofMinutes(5)));
        assertThat(created.getActivity()).isEqualTo(OnlinePurchaseMother.build());
        assertThat(created.getDeliveryMethod()).isEqualTo(DeliveryMethodMother.eligible());
        assertThat(created.getConfig()).isEqualTo(OtpConfigMother.build());
        assertThat(created.isProtectSensitiveData()).isTrue();
    }

    @Test
    void shouldPopulateOtpValuesOnVerification() {
        SendOtpRequest request = buildSuccessfulSendOtpRequest();

        OtpVerification created = application.sendOtp(request);

        assertThat(created.getId()).isEqualTo(UUID.fromString("81e11840-140e-45ac-a6af-40aa653a146e"));
        assertThat(created.isSuccessful()).isFalse();
        assertThat(created.isComplete()).isFalse();
        Deliveries deliveries = created.getDeliveries();
        assertThat(deliveries.getMax()).isEqualTo(2);
        assertThat(deliveries.getValues()).containsExactly(expectedDelivery());
    }

    @Test
    void shouldSaveVerification() {
        SendOtpRequest request = buildSuccessfulSendOtpRequest();
        OtpVerification created = application.sendOtp(request);

        OtpVerification retrieved = application.getOtp(created.getId());

        assertThat(retrieved).isEqualTo(created);
    }

    private static SendOtpRequest buildSuccessfulSendOtpRequest() {
        return SendOtpRequest.builder()
                .contextId(UUID.fromString("2a3559bd-0071-4bbf-8901-42b9f17dd93f"))
                .deliveryMethodId(UUID.fromString("c9959188-969e-42f3-8178-42ef824c81d3"))
                .build();
    }

    private Delivery expectedDelivery() {
        return DeliveryMother.builder()
                .sent(harness.now())
                .message(toExpectedMessage())
                .messageId("76c9ec3b-b7aa-41ae-8066-796856e71e65")
                .build();
    }

    private Message toExpectedMessage() {
        return MessageMother.withPasscode(toExpectedPasscode());
    }

    private Passcode toExpectedPasscode() {
        return PasscodeMother.builder()
                .created(harness.now())
                .expiry(harness.now().plus(Duration.ofMinutes(2)))
                .value("00000001")
                .build();
    }

}
