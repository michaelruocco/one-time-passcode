package uk.co.idv.otp.adapter.verificationloader;

import uk.co.idv.context.adapter.client.VerificationClient;
import uk.co.idv.context.adapter.client.exception.ApiErrorClientException;
import uk.co.idv.context.adapter.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.context.adapter.client.request.ClientCreateVerificationRequest;
import uk.co.idv.context.adapter.json.error.contextexpired.ContextExpiredError;
import uk.co.idv.context.adapter.json.error.contextnotfound.ContextNotFoundError;
import uk.co.idv.context.entities.activity.OnlinePurchaseMother;
import uk.co.idv.context.entities.context.method.MethodsMother;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.context.entities.verification.VerificationMother;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.otp.OtpMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodsMother;

import java.util.UUID;

import static uk.co.idv.otp.adapter.verificationloader.StubContextConstants.CONTEXT_EXPIRED_ID;
import static uk.co.idv.otp.adapter.verificationloader.StubContextConstants.CONTEXT_EXPIRY;
import static uk.co.idv.otp.adapter.verificationloader.StubContextConstants.CONTEXT_NOT_FOUND_ID;
import static uk.co.idv.otp.adapter.verificationloader.StubContextConstants.DELIVERY_METHOD_NOT_ELIGIBLE_ID;
import static uk.co.idv.otp.adapter.verificationloader.StubContextConstants.DELIVERY_METHOD_NOT_FOUND_ID;
import static uk.co.idv.otp.adapter.verificationloader.StubContextConstants.OTP_METHOD_NOT_FOUND_ID;

public class StubVerificationClient implements VerificationClient {

    @Override
    public Verification createVerification(ClientCreateVerificationRequest request) {
        UUID contextId = request.getContextId();
        switch (contextId.toString()) {
            case CONTEXT_NOT_FOUND_ID:
                throw new ApiErrorClientException(new ContextNotFoundError(contextId.toString()));
            case CONTEXT_EXPIRED_ID:
                throw new ApiErrorClientException(new ContextExpiredError(contextId, CONTEXT_EXPIRY));
            case OTP_METHOD_NOT_FOUND_ID:
                return toNotFoundVerification(contextId);
            case DELIVERY_METHOD_NOT_FOUND_ID:
                return VerificationMother.withMethod(toOtpMethod(DeliveryMethodsMother.empty()));
            case DELIVERY_METHOD_NOT_ELIGIBLE_ID:
                return VerificationMother.withMethod(toOtpMethod(DeliveryMethodMother.ineligible()));
            default:
                return VerificationMother.builder()
                        .contextId(contextId)
                        .activity(OnlinePurchaseMother.build())
                        .methods(MethodsMother.with(toOtpMethod(DeliveryMethodMother.eligible())))
                        .build();
        }
    }

    @Override
    public Verification completeVerification(ClientCompleteVerificationRequest request) {
        return VerificationMother.successful();
    }

    private static Verification toNotFoundVerification(UUID contextId) {
        return VerificationMother.builder()
                .contextId(contextId)
                .methods(MethodsMother.empty())
                .build();
    }

    private static Method toOtpMethod(DeliveryMethod deliveryMethod) {
        return toOtpMethod(DeliveryMethodsMother.with(deliveryMethod));
    }

    private static Method toOtpMethod(DeliveryMethods deliveryMethods) {
        return OtpMother.withDeliveryMethods(deliveryMethods);
    }

}
