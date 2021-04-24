package uk.co.idv.otp.usecases.send;

import lombok.Builder;
import uk.co.idv.otp.entities.delivery.DefaultDeliveryRequest;
import uk.co.idv.otp.entities.send.message.GenerateMessageRequest;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.idv.otp.entities.send.SendOtpRequest;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.usecases.OtpVerificationRepository;
import uk.co.idv.otp.usecases.passcode.PasscodeGenerator;
import uk.co.idv.otp.usecases.send.deliver.DeliverOtp;
import uk.co.idv.otp.usecases.send.message.MessageGenerator;

@Builder
public class SendOtp {

    private final OtpVerificationLoader verificationLoader;
    private final PasscodeGenerator passcodeGenerator;
    private final MessageGenerator messageGenerator;
    private final DeliverOtp deliverOtp;
    private final OtpVerificationRepository repository;

    public OtpVerification send(SendOtpRequest request) {
        var verification = verificationLoader.load(request);
        var delivery = deliver(verification);
        var updated = verification.add(delivery);
        repository.save(updated);
        return updated;
    }

    private Delivery deliver(OtpVerification verification) {
        var request = DefaultDeliveryRequest.builder()
                .method(verification.getDeliveryMethod())
                .message(generateMessage(verification))
                .build();
        return deliverOtp.deliver(request);
    }

    private Message generateMessage(OtpVerification verification) {
        var messageRequest = GenerateMessageRequest.builder()
                .passcode(passcodeGenerator.generate(verification))
                .activity(verification.getActivity())
                .build();
        return messageGenerator.apply(messageRequest);
    }

}
