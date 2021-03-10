package uk.co.idv.otp.config.delivery;

import uk.co.idv.otp.usecases.send.deliver.DeliverOtp;

public interface DeliveryConfig {

    DeliverOtp deliverOtp();

}
