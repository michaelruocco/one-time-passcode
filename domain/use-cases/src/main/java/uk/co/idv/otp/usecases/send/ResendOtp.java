package uk.co.idv.otp.usecases.send;

import lombok.Builder;
import uk.co.idv.otp.entities.delivery.DefaultDeliveryRequest;
import uk.co.idv.otp.entities.send.ResendOtpRequest;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.usecases.OtpVerificationRepository;
import uk.co.idv.otp.usecases.get.GetOtp;
import uk.co.idv.otp.usecases.passcode.PasscodeGenerator;
import uk.co.idv.otp.usecases.send.deliver.DeliverOtp;
import uk.co.idv.otp.usecases.send.message.MessageGenerator;


@Builder
public class ResendOtp {

    private final GetOtp getOtp;
    private final PasscodeGenerator passcodeGenerator;
    private final MessageGenerator messageGenerator;
    private final DeliverOtp deliverOtp;
    private final OtpVerificationRepository repository;

    public OtpVerification resend(ResendOtpRequest request) {
        var id = request.getId();
        var originalVerification = getOtp.get(id);
        var resentDelivery = redeliver(originalVerification);
        var updatedVerification = originalVerification.add(resentDelivery);
        repository.save(updatedVerification);
        return updatedVerification;
    }

    private Delivery redeliver(OtpVerification verification) {
        var firstMessage = verification.getFirstMessage();
        var passcode = passcodeGenerator.generate(verification);
        var deliveryRequest = DefaultDeliveryRequest.builder()
                .method(verification.getDeliveryMethod())
                .message(firstMessage.update(passcode))
                .build();
        return deliverOtp.deliver(deliveryRequest);
    }

}
