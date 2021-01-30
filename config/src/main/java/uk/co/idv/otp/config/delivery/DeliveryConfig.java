package uk.co.idv.otp.config.delivery;

import uk.co.idv.otp.usecases.send.DeliverOtp;

public interface DeliveryConfig {

    DeliverOtp deliverOtp();

}
