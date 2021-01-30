package uk.co.idv.otp.adapter.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.OtpVerificationMother;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MongoOtpVerificationRepositoryTest {

    private final MongoCollection<Document> collection = mock(MongoCollection.class);
    private final OtpVerificationConverter verificationConverter = mock(OtpVerificationConverter.class);

    private final MongoOtpVerificationRepository repository = MongoOtpVerificationRepository.builder()
            .collection(collection)
            .verificationConverter(verificationConverter)
            .build();

    @Test
    void shouldSaveVerification() {
        OtpVerification verification = OtpVerificationMother.incomplete();
        Bson query = givenConvertsToFindByIdQuery(verification.getId());
        Document document = givenConvertsToMongoDocument(verification);

        repository.save(verification);

        ArgumentCaptor<ReplaceOptions> captor = ArgumentCaptor.forClass(ReplaceOptions.class);
        verify(collection).replaceOne(eq(query), eq(document), captor.capture());
        ReplaceOptions options = captor.getValue();
        assertThat(options.isUpsert()).isTrue();
    }

    @Test
    void shouldLoadVerificationIfPresent() {
        UUID id = UUID.randomUUID();
        Bson query = givenConvertsToFindByIdQuery(id);
        FindIterable<Document> documents = givenDocumentsReturnedForQuery(query);
        Document document = givenHasFirstDocument(documents);
        OtpVerification expectedVerification = givenConvertsToVerification(document);

        Optional<OtpVerification> verification = repository.load(id);

        assertThat(verification).contains(expectedVerification);
    }

    @Test
    void shouldLoadEmptyVerificationIfNotPresent() {
        UUID id = UUID.randomUUID();
        Bson query = givenConvertsToFindByIdQuery(id);
        FindIterable<Document> documents = givenDocumentsReturnedForQuery(query);
        given(documents.first()).willReturn(null);

        Optional<OtpVerification> verification = repository.load(id);

        assertThat(verification).isEmpty();
    }

    private Bson givenConvertsToFindByIdQuery(UUID id) {
        Bson query = mock(Bson.class);
        given(verificationConverter.toFindByIdQuery(id)).willReturn(query);
        return query;
    }

    private Document givenConvertsToMongoDocument(OtpVerification verification) {
        Document document = mock(Document.class);
        given(verificationConverter.toDocument(verification)).willReturn(document);
        return document;
    }

    private FindIterable<Document> givenDocumentsReturnedForQuery(Bson query) {
        FindIterable<Document> documents = mock(FindIterable.class);
        given(collection.find(query)).willReturn(documents);
        return documents;
    }

    private Document givenHasFirstDocument(FindIterable<Document> documents) {
        Document document = mock(Document.class);
        given(documents.first()).willReturn(document);
        return document;
    }

    private OtpVerification givenConvertsToVerification(Document document) {
        OtpVerification verification = OtpVerificationMother.incomplete();
        given(verificationConverter.toVerification(document)).willReturn(verification);
        return verification;
    }

}
