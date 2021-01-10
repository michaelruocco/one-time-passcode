package uk.co.idv.otp.usecases.send;

import lombok.Builder;
import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.idv.otp.entities.send.ResendOtpRequest;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.usecases.OtpVerificationRepository;
import uk.co.idv.otp.usecases.get.GetOtp;
import uk.co.idv.otp.usecases.passcode.PasscodeGenerator;
import uk.co.idv.otp.usecases.send.message.MessageGenerator;

import java.util.UUID;

@Builder
public class ResendOtp {

    private final GetOtp getOtp;
    private final PasscodeGenerator passcodeGenerator;
    private final MessageGenerator messageGenerator;
    private final DeliverOtp deliverOtp;
    private final OtpVerificationRepository repository;

    public OtpVerification resend(ResendOtpRequest request) {
        UUID id = request.getId();
        OtpVerification originalVerification = getOtp.get(id);
        Delivery resentDelivery = redeliver(originalVerification);
        OtpVerification updatedVerification = originalVerification.add(resentDelivery);
        repository.save(updatedVerification);
        return updatedVerification;
    }

    private Delivery redeliver(OtpVerification verification) {
        Message firstMessage = verification.getFirstMessage();
        Passcode passcode = passcodeGenerator.generate(verification);
        DeliveryRequest deliveryRequest = DeliveryRequest.builder()
                .method(verification.getDeliveryMethod())
                .message(firstMessage.update(passcode))
                .build();
        return deliverOtp.deliver(deliveryRequest);
    }

}
