package uk.co.idv.otp.entities.delivery;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.entities.send.message.Message;

import java.time.Instant;
import java.util.Optional;

@Builder(toBuilder = true)
@Data
public class Delivery {

    private final DeliveryMethod method;
    private final Message message;
    private final String messageId;
    private final Instant sent;

    public String getMessageText() {
        return message.getText();
    }

    public Passcode getPasscode() {
        return message.getPasscode();
    }

    public Optional<Passcode> getPasscodeIfValid(Instant now) {
        return message.getPasscodeIfValid(now);
    }

}
