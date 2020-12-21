package uk.co.idv.otp.usecases.send;

import lombok.Builder;
import uk.co.idv.otp.entities.send.DeliveryRequest;
import uk.co.idv.otp.entities.verification.Message;
import uk.co.idv.otp.entities.send.ResendOtpRequest;
import uk.co.idv.otp.entities.verification.Delivery;
import uk.co.idv.otp.entities.verification.Verification;
import uk.co.idv.otp.usecases.VerificationDao;

import java.util.UUID;

@Builder
public class ResendOtp {

    private final PasscodeGenerator passcodeGenerator;
    private final MessageGenerator messageGenerator;
    private final DeliverOtp deliverOtp;
    private final VerificationDao dao;

    public Verification resend(ResendOtpRequest request) {
        UUID id = request.getVerificationId();
        Verification verification = dao.load(id);
        Delivery resentDelivery = redeliver(verification.getFirstDelivery());
        verification.add(resentDelivery);
        dao.save(verification);
        return verification;
    }

    private Delivery redeliver(Delivery originalDelivery) {
        Message originalMessage = originalDelivery.getMessage();
        DeliveryRequest request = DeliveryRequest.builder()
                .method(originalDelivery.getMethod())
                .message(originalMessage.update(passcodeGenerator.generate()))
                .build();
        return deliverOtp.deliver(request);
    }

}
