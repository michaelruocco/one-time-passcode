package uk.co.idv.otp.entities.send.message;

import uk.co.idv.activity.entities.Activity;
import uk.co.idv.activity.entities.OnlinePurchaseMother;
import uk.co.idv.otp.entities.passcode.PasscodeMother;

public interface GenerateMessageRequestMother {

    static GenerateMessageRequest build() {
        return builder().build();
    }

    static GenerateMessageRequest withActivity(Activity activity) {
        return builder().activity(activity).build();
    }

    static GenerateMessageRequest.GenerateMessageRequestBuilder builder() {
        return GenerateMessageRequest.builder()
                .passcode(PasscodeMother.build())
                .activity(OnlinePurchaseMother.build());
    }

}
