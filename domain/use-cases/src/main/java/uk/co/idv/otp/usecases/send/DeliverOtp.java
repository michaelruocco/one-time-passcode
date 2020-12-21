package uk.co.idv.otp.usecases.send;

import uk.co.idv.otp.entities.send.DeliveryRequest;
import uk.co.idv.otp.entities.verification.Delivery;

public interface DeliverOtp {

    Delivery deliver(DeliveryRequest request);

}
