package uk.co.idv.otp.entities.send;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.otp.entities.verification.Passcode;

@Builder
@Data
public class GenerateMessageRequest {

    private final Passcode passcode;
    private final Activity activity;

    public String getActivityName() {
        return activity.getName();
    }

}
