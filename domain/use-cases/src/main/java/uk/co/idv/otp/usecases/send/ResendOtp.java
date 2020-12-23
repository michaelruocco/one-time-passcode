package uk.co.idv.otp.usecases.send;

import lombok.Builder;
import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.idv.otp.entities.send.ResendOtpRequest;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.Verification;
import uk.co.idv.otp.usecases.VerificationRepository;
import uk.co.idv.otp.usecases.passcode.PasscodeGenerator;
import uk.co.idv.otp.usecases.send.message.MessageGenerator;

import java.util.UUID;

@Builder
public class ResendOtp {

    private final PasscodeGenerator passcodeGenerator;
    private final MessageGenerator messageGenerator;
    private final DeliverOtp deliverOtp;
    private final VerificationRepository repository;

    public Verification resend(ResendOtpRequest request) {
        UUID id = request.getVerificationId();
        Verification verification = repository.load(id);
        Delivery resentDelivery = redeliver(verification);
        verification.add(resentDelivery);
        repository.save(verification);
        return verification;
    }

    private Delivery redeliver(Verification verification) {
        Message firstMessage = verification.getFirstMessage();
        Passcode passcode = passcodeGenerator.generate(verification);
        DeliveryRequest deliveryRequest = DeliveryRequest.builder()
                .method(verification.getDeliveryMethod())
                .message(firstMessage.update(passcode))
                .build();
        return deliverOtp.deliver(deliveryRequest);
    }

}
