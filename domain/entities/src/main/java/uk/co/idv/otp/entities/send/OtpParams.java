package uk.co.idv.otp.entities.send;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.method.entities.otp.OtpConfig;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;

@Builder
@Data
public class OtpParams {

    private final DeliveryMethod deliveryMethod;
    private final OtpConfig otpConfig;

    public int getMaxNumberOfPasscodeDeliveries() {
        return otpConfig.getMaxNumberOfPasscodeDeliveries();
    }

}
