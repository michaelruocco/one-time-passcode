package uk.co.idv.otp.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.mruoc.json.JsonConverter;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

@RequiredArgsConstructor
public class OtpVerificationConverter {

    private final JsonConverter converter;

    public OtpVerification toVerification(Document document) {
        return converter.toObject(document.toJson(), OtpVerification.class);
    }

    public Document toDocument(OtpVerification verification) {
        Document document = Document.parse(converter.toJson(verification));
        document.put("_id", verification.getId().toString());
        return document;
    }

    public Bson toFindByIdQuery(UUID id) {
        return eq("_id", id.toString());
    }

}
