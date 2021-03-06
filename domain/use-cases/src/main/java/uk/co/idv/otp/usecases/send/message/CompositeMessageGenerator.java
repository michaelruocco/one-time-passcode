package uk.co.idv.otp.usecases.send.message;

import lombok.RequiredArgsConstructor;
import uk.co.idv.otp.entities.send.message.GenerateMessageRequest;
import uk.co.idv.otp.entities.send.message.Message;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
public class CompositeMessageGenerator implements MessageGenerator {

    private final Collection<ActivityMessageGenerator> generators;

    public CompositeMessageGenerator() {
        this(new OnlinePurchaseMessageGenerator());
    }

    public CompositeMessageGenerator(ActivityMessageGenerator... generators) {
        this(Arrays.asList(generators));
    }

    @Override
    public Message apply(GenerateMessageRequest request) {
        return generators.stream()
                .map(generator -> generator.apply(request))
                .flatMap(Optional::stream)
                .findFirst()
                .orElseThrow(() -> new ActivityNotSupportedException(request.getActivityName()));
    }

}
