package uk.co.idv.otp.entities.send;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.activity.OnlinePurchaseMother;
import uk.co.idv.otp.entities.verification.Passcode;
import uk.co.idv.otp.entities.verification.PasscodeMother;

import static org.assertj.core.api.Assertions.assertThat;

class GenerateMessageRequestTest {

    @Test
    void shouldReturnPasscode() {
        Passcode passcode = PasscodeMother.build();

        GenerateMessageRequest request = GenerateMessageRequest.builder()
                .passcode(passcode)
                .build();

        assertThat(request.getPasscode()).isEqualTo(passcode);
    }

    @Test
    void shouldReturnActivity() {
        Activity activity = OnlinePurchaseMother.build();

        GenerateMessageRequest request = GenerateMessageRequest.builder()
                .activity(activity)
                .build();

        assertThat(request.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldReturnActivityName() {
        Activity activity = OnlinePurchaseMother.build();

        GenerateMessageRequest request = GenerateMessageRequest.builder()
                .activity(activity)
                .build();

        assertThat(request.getActivityName()).isEqualTo(activity.getName());
    }

}
