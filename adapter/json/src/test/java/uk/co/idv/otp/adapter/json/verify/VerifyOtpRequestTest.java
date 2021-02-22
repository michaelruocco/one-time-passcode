package uk.co.idv.otp.adapter.json.verify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import uk.co.idv.otp.adapter.json.OtpAppModule;
import uk.co.idv.otp.entities.verify.VerifyOtpRequest;
import uk.co.idv.otp.entities.verify.VerifyOtpRequestMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class VerifyOtpRequestTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(new OtpAppModule());
    private static final VerifyOtpRequest REQUEST = VerifyOtpRequestMother.build();
    private static final String JSON = VerifyOtpRequestJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(REQUEST);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        VerifyOtpRequest request = MAPPER.readValue(JSON, VerifyOtpRequest.class);

        assertThat(request).isEqualTo(REQUEST);
    }

}
