package uk.co.idv.otp.adapter.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.otp.entities.OtpVerification;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class OtpVerificationSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(new OtpAppModule());

    @ParameterizedTest(name = "should serialize otp verification {1}")
    @ArgumentsSource(OtpVerificationArgumentsProvider.class)
    void shouldSerialize(String expectedJson, OtpVerification verification) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(verification);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize otp verification {1}")
    @ArgumentsSource(OtpVerificationArgumentsProvider.class)
    void shouldDeserialize(String json, OtpVerification expectedVerification) throws JsonProcessingException {
        OtpVerification verification = MAPPER.readValue(json, OtpVerification.class);

        assertThat(verification).usingRecursiveComparison().isEqualTo(expectedVerification);
    }

}
