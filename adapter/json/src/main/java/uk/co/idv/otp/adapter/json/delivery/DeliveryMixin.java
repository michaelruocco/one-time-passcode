package uk.co.idv.otp.adapter.json.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.otp.entities.passcode.Passcode;

public interface DeliveryMixin {

    @JsonIgnore
    String getMessageText();

    @JsonIgnore
    Passcode getPasscode();

}
