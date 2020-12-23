package uk.co.idv.otp.usecases.send;

import lombok.Builder;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.entities.send.message.GenerateMessageRequest;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.idv.otp.entities.send.SendOtpRequest;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.Verification;
import uk.co.idv.otp.usecases.VerificationRepository;
import uk.co.idv.otp.usecases.passcode.PasscodeGenerator;
import uk.co.idv.otp.usecases.send.message.MessageGenerator;

@Builder
public class SendOtp {

    private final VerificationLoader verificationLoader;
    private final PasscodeGenerator passcodeGenerator;
    private final MessageGenerator messageGenerator;
    private final DeliverOtp deliverOtp;
    private final VerificationRepository repository;

    public Verification send(SendOtpRequest request) {
        Verification verification = verificationLoader.send(request);
        Delivery delivery = deliver(verification);
        verification.add(delivery);
        repository.save(verification);
        return verification;
    }

    private Message generateMessage(Verification verification) {
        GenerateMessageRequest messageRequest = GenerateMessageRequest.builder()
                .passcode(passcodeGenerator.generate(verification))
                .activity(verification.getActivity())
                .build();
        return messageGenerator.apply(messageRequest);
    }

    private Delivery deliver(Verification verification) {
        DeliveryRequest request = DeliveryRequest.builder()
                .method(verification.getDeliveryMethod())
                .message(generateMessage(verification))
                .build();
        return deliverOtp.deliver(request);
    }

}
