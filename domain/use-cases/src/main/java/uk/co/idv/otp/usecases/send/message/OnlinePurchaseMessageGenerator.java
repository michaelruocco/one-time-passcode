package uk.co.idv.otp.usecases.send.message;

import uk.co.idv.activity.entities.onlinepurchase.OnlinePurchase;
import uk.co.idv.otp.entities.send.message.GenerateMessageRequest;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.idv.otp.entities.passcode.Passcode;

import java.util.Optional;

public class OnlinePurchaseMessageGenerator implements ActivityMessageGenerator {

    private static final String TEMPLATE = "Use one time code %s " +
            "to make a payment of %s to %s with card ending %s. " +
            "Never share this code with anyone.";

    @Override
    public Optional<Message> apply(GenerateMessageRequest request) {
        var activity = request.getActivity();
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
                onlinePurchase.getLast4CardNumberDigits()
        );
    }

}
