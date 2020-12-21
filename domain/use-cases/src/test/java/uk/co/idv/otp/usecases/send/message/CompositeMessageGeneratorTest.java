package uk.co.idv.otp.usecases.send.message;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.send.GenerateMessageRequest;
import uk.co.idv.otp.entities.send.GenerateMessageRequestMother;
import uk.co.idv.otp.entities.verification.Message;
import uk.co.idv.otp.entities.verification.MessageMother;
import uk.co.idv.otp.usecases.send.MessageGenerator;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CompositeMessageGeneratorTest {

    private final ActivityMessageGenerator activityGenerator1 = mock(ActivityMessageGenerator.class);
    private final ActivityMessageGenerator activityGenerator2 = mock(ActivityMessageGenerator.class);

    private final MessageGenerator generator = new CompositeMessageGenerator(activityGenerator1, activityGenerator2);

    @Test
    void shouldThrowExceptionIfNoSupportingGeneratorsForActivity() {
        GenerateMessageRequest request = GenerateMessageRequestMother.build();
        given(activityGenerator1.apply(request)).willReturn(Optional.empty());
        given(activityGenerator2.apply(request)).willReturn(Optional.empty());

        Throwable error = catchThrowable(() -> generator.apply(request));

        assertThat(error)
                .isInstanceOf(ActivityNotSupportedException.class)
                .hasMessage(request.getActivityName());
    }

    @Test
    void shouldReturnMessageFromFirstGeneratorThatReturnsMessage() {
        GenerateMessageRequest request = GenerateMessageRequestMother.build();
        given(activityGenerator1.apply(request)).willReturn(Optional.empty());
        Message expectedMessage = MessageMother.build();
        given(activityGenerator2.apply(request)).willReturn(Optional.of(expectedMessage));

        Message message = generator.apply(request);

        assertThat(message).isEqualTo(expectedMessage);
    }

}
