package uk.co.idv.otp.usecases.send.message;

import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.activity.OnlinePurchase;
import uk.co.idv.otp.entities.send.GenerateMessageRequest;
import uk.co.idv.otp.entities.verification.Message;
import uk.co.idv.otp.entities.verification.Passcode;

import java.util.Optional;

public class OnlinePurchaseMessageGenerator implements ActivityMessageGenerator {

    private static final String TEMPLATE = "Use one time code %s " +
            "to make a payment of %s to %s with card ending %s. " +
            "Never share this code with anyone.";

    @Override
    public Optional<Message> apply(GenerateMessageRequest request) {
        Activity activity = request.getActivity();
        if (activity instanceof OnlinePurchase) {
            return Optional.of(toMessage(request.getPasscode(), (OnlinePurchase) activity));
        }
        return Optional.empty();
    }

    private Message toMessage(Passcode passcode, OnlinePurchase onlinePurchase) {
        return Message.builder()
                .passcode(passcode)
                .text(toText(passcode, onlinePurchase))
                .build();
    }

    private String toText(Passcode passcode, OnlinePurchase onlinePurchase) {
        return String.format(TEMPLATE,
                passcode.getValue(),
                onlinePurchase.getCost().toString(),
                onlinePurchase.getMerchantName(),
                onlinePurchase.getLast4DigitsOfCardNumber()
        );
    }

}
