package uk.co.idv.otp.usecases.send;

import uk.co.idv.otp.entities.send.GenerateMessageRequest;
import uk.co.idv.otp.entities.verification.Message;

import java.util.function.Function;

public interface MessageGenerator extends Function<GenerateMessageRequest, Message> {

    // intentionally blank

}
