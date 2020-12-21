package uk.co.idv.otp.usecases.send;

import lombok.Builder;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.otp.entities.send.DeliveryRequest;
import uk.co.idv.otp.entities.send.GenerateMessageRequest;
import uk.co.idv.otp.entities.send.LoadContextRequest;
import uk.co.idv.otp.entities.verification.Message;
import uk.co.idv.otp.entities.send.SendOtpRequest;
import uk.co.idv.otp.entities.verification.Delivery;
import uk.co.idv.otp.entities.verification.OtpDeliveryMethod;
import uk.co.idv.otp.entities.verification.Verification;
import uk.co.idv.otp.usecases.VerificationDao;

@Builder
public class SendOtp {

    private final ContextLoader contextLoader;
    private final DeliveryMethodExtractor deliveryMethodExtractor;
    private final ContextConverter contextConverter;
    private final PasscodeGenerator passcodeGenerator;
    private final MessageGenerator messageGenerator;
    private final DeliverOtp deliverOtp;
    private final VerificationDao dao;

    public Verification send(SendOtpRequest request) {
        Context context = contextLoader.load(toLoadContextRequest(request));
        Verification verification = contextConverter.toVerification(context);
        Message message = generateMessage(context.getActivity());
        Delivery delivery = deliver(deliveryMethodExtractor.extract(context), message);
        verification.add(delivery);
        dao.save(verification);
        return verification;
    }

    private LoadContextRequest toLoadContextRequest(SendOtpRequest request) {
        return LoadContextRequest.builder()
                .contextId(request.getContextId())
                .deliveryMethodId(request.getDeliveryMethodId())
                .build();
    }

    private Message generateMessage(Activity activity) {
        GenerateMessageRequest request = GenerateMessageRequest.builder()
                .passcode(passcodeGenerator.generate())
                .activity(activity)
                .build();
        return messageGenerator.generate(request);
    }

    private Delivery deliver(OtpDeliveryMethod method, Message message) {
        DeliveryRequest request = DeliveryRequest.builder()
                .method(method)
                .message(message)
                .build();
        return deliverOtp.deliver(request);
    }

}
