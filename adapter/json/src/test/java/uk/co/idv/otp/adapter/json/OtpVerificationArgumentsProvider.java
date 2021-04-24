package uk.co.idv.otp.adapter.json;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.otp.entities.OtpVerificationMother;

import java.util.stream.Stream;

public class OtpVerificationArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(OtpVerificationJsonMother.incompleteSms(), OtpVerificationMother.incomplete())
        );
    }

}
