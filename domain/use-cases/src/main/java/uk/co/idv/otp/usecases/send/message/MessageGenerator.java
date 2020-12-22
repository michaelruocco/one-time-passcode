package uk.co.idv.otp.usecases.send.message;

import uk.co.idv.otp.entities.send.message.GenerateMessageRequest;
import uk.co.idv.otp.entities.send.message.Message;

import java.util.function.Function;

public interface MessageGenerator extends Function<GenerateMessageRequest, Message> {

    // intentionally blank

}
