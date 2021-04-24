package uk.co.idv.otp.adapter.json.protect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.otp.adapter.json.OtpAppModule;
import uk.co.idv.otp.adapter.json.OtpVerificationJsonMother;

import java.util.function.UnaryOperator;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class OtpVerificationJsonMaskerTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new OtpAppModule());

    private final UnaryOperator<String> masker = new OtpVerificationJsonMasker(MAPPER);

    @Test
    void shouldMaskVerificationWithSmsDeliveryMethod() {
        String json = OtpVerificationJsonMother.incompleteSms();

        String maskedJson = masker.apply(json);

        assertThatJson(maskedJson).isEqualTo(OtpVerificationJsonMother.incompleteSmsMasked());
    }

    @Test
    void shouldMaskVerificationWithVoiceDeliveryMethod() {
        String json = OtpVerificationJsonMother.incompleteVoice();

        String maskedJson = masker.apply(json);

        assertThatJson(maskedJson).isEqualTo(OtpVerificationJsonMother.incompleteVoiceMasked());
    }

    @Test
    void shouldMaskVerificationWithEmailDeliveryMethod() {
        String json = OtpVerificationJsonMother.incompleteEmail();

        String maskedJson = masker.apply(json);

        assertThatJson(maskedJson).isEqualTo(OtpVerificationJsonMother.incompleteEmailMasked());
    }

}
