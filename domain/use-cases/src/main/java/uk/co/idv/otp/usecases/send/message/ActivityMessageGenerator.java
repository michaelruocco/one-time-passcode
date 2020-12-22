package uk.co.idv.otp.usecases.send.message;

import uk.co.idv.otp.entities.send.message.GenerateMessageRequest;
import uk.co.idv.otp.entities.send.message.Message;

import java.util.Optional;
import java.util.function.Function;

public interface ActivityMessageGenerator extends Function<GenerateMessageRequest, Optional<Message>> {

    // intentionally blank

}
