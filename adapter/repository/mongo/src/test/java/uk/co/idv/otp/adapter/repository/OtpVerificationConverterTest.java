package uk.co.idv.otp.adapter.repository;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import uk.co.idv.otp.adapter.json.OtpVerificationJsonMother;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.OtpVerificationMother;
import uk.co.mruoc.json.JsonConverter;

import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OtpVerificationConverterTest {

    private static final String JSON = OtpVerificationJsonMother.incompleteSms();

    private final JsonConverter jsonConverter = mock(JsonConverter.class);

    private final OtpVerificationConverter converter = new OtpVerificationConverter(jsonConverter);

    @Test
    void shouldConvertDocumentToOtpVerification() {
        Document document = givenDocumentWithJson();
        OtpVerification expectedVerification = givenJsonConvertsToVerification();

        OtpVerification verification = converter.toVerification(document);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldConvertVerificationToDocument() {
        OtpVerification verification = OtpVerificationMother.incomplete();
        givenConvertsToJson(verification);

        Document document = converter.toDocument(verification);

        assertThatJson(document.toJson())
                .whenIgnoringPaths("_id", "ttl")
                .isEqualTo(JSON);
        assertThatJson(document.toJson())
                .inPath("_id")
                .isEqualTo(verification.getId().toString());
        assertThatJson(document.toJson())
                .inPath("ttl")
                .isEqualTo("{\"$date\":1600114081999}");
    }

    @Test
    void shouldConvertIdToFindByIdQuery() {
        UUID id = UUID.fromString("8bd8741e-da0b-467c-a5fe-ebd9e2030a19");

        Bson query = converter.toFindByIdQuery(id);

        assertThat(query).hasToString(
                "Filter{fieldName='_id', value=8bd8741e-da0b-467c-a5fe-ebd9e2030a19}"
        );
    }

    private Document givenDocumentWithJson() {
        Document document = mock(Document.class);
        given(document.toJson()).willReturn(JSON);
        return document;
    }

    private OtpVerification givenJsonConvertsToVerification() {
        OtpVerification verification = OtpVerificationMother.incomplete();
        given(jsonConverter.toObject(JSON, OtpVerification.class)).willReturn(verification);
        return verification;
    }

    private void givenConvertsToJson(OtpVerification verification) {
        given(jsonConverter.toJson(verification)).willReturn(JSON);
    }

}
