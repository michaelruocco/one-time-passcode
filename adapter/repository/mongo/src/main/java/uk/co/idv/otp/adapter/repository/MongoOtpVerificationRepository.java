package uk.co.idv.otp.adapter.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Builder;
import org.bson.Document;
import org.bson.conversions.Bson;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.usecases.OtpVerificationRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static uk.co.mruoc.duration.logger.MongoMdcDurationLoggerUtils.logDuration;

@Builder
public class MongoOtpVerificationRepository implements OtpVerificationRepository {

    private final MongoCollection<Document> collection;
    private final OtpVerificationConverter verificationConverter;

    @Override
    public void save(OtpVerification verification) {
        var start = Instant.now();
        try {
            var query = toFindByIdQuery(verification.getId());
            var options = new ReplaceOptions().upsert(true);
            var document = verificationConverter.toDocument(verification);
            collection.replaceOne(query, document, options);
        } finally {
            logDuration("save-attempts", start);
        }
    }

    @Override
    public Optional<OtpVerification> load(UUID id) {
        var start = Instant.now();
        try {
            var query = toFindByIdQuery(id);
            var documents = collection.find(query);
            return Optional.ofNullable(documents.first()).map(this::toVerification);
        } finally {
            logDuration("load-attempts-by-idv-id", start);
        }
    }

    private Bson toFindByIdQuery(UUID id) {
        return verificationConverter.toFindByIdQuery(id);
    }

    private OtpVerification toVerification(Document document) {
        return verificationConverter.toVerification(document);
    }

}
