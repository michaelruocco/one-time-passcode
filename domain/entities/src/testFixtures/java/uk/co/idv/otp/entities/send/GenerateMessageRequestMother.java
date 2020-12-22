package uk.co.idv.otp.entities.send;

import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.activity.OnlinePurchaseMother;
import uk.co.idv.otp.entities.send.message.GenerateMessageRequest;
import uk.co.idv.otp.entities.verification.PasscodeMother;

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
