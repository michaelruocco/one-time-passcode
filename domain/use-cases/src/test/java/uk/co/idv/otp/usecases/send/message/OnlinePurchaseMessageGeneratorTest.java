package uk.co.idv.otp.usecases.send.message;

import org.junit.jupiter.api.Test;
import uk.co.idv.activity.entities.Activity;
import uk.co.idv.activity.entities.LoginMother;
import uk.co.idv.activity.entities.OnlinePurchaseMother;
import uk.co.idv.otp.entities.send.message.GenerateMessageRequest;
import uk.co.idv.otp.entities.send.message.GenerateMessageRequestMother;
import uk.co.idv.otp.entities.send.message.Message;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OnlinePurchaseMessageGeneratorTest {

    private final ActivityMessageGenerator generator = new OnlinePurchaseMessageGenerator();

    @Test
    void shouldReturnEmptyIfActivityIsNotOnlinePurchase() {
        Activity activity = LoginMother.login();
        GenerateMessageRequest request = GenerateMessageRequestMother.withActivity(activity);

        Optional<Message> message = generator.apply(request);

        assertThat(message).isEmpty();
    }

    @Test
    void shouldMessageWithPasscodeIfActivityIsOnlinePurchase() {
        Activity activity = OnlinePurchaseMother.build();
        GenerateMessageRequest request = GenerateMessageRequestMother.withActivity(activity);

        Optional<Message> message = generator.apply(request);

        assertThat(message).map(Message::getPasscode).contains(request.getPasscode());
    }

    @Test
    void shouldMessageWithTextIfActivityIsOnlinePurchase() {
        Activity activity = OnlinePurchaseMother.build();
        GenerateMessageRequest request = GenerateMessageRequestMother.withActivity(activity);

        Optional<Message> message = generator.apply(request);

        assertThat(message).map(Message::getText).contains(
                "Use one time code 87654321 " +
                "to make a payment of GBP 10.99 " +
                "to Amazon with card ending 1111. " +
                "Never share this code with anyone."
        );
    }

}
