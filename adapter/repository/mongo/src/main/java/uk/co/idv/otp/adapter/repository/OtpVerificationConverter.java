package uk.co.idv.otp.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.mruoc.json.JsonConverter;

import java.util.Date;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;
import static uk.co.idv.otp.adapter.repository.OtpVerificationCollection.ID_FIELD_NAME;
import static uk.co.idv.otp.adapter.repository.OtpVerificationCollection.TTL_INDEX_NAME;

@RequiredArgsConstructor
public class OtpVerificationConverter {

    private final JsonConverter converter;

    public OtpVerification toVerification(Document document) {
        return converter.toObject(document.toJson(), OtpVerification.class);
    }

    public Document toDocument(OtpVerification verification) {
        Document document = Document.parse(converter.toJson(verification));
        document.put(ID_FIELD_NAME, verification.getId().toString());
        document.put(TTL_INDEX_NAME, new Date(verification.getExpiry().toEpochMilli()));
        return document;
    }

    public Bson toFindByIdQuery(UUID id) {
        return eq(ID_FIELD_NAME, id.toString());
    }

}
