package uk.co.idv.otp.config.repository;

import com.mongodb.client.MongoDatabase;
import lombok.Builder;
import uk.co.idv.otp.adapter.repository.MongoOtpVerificationRepository;
import uk.co.idv.otp.adapter.repository.OtpVerificationConverter;
import uk.co.idv.otp.config.RepositoryConfig;
import uk.co.idv.otp.usecases.OtpVerificationRepository;
import uk.co.mruoc.json.JsonConverter;

import static uk.co.idv.otp.adapter.repository.OtpVerificationCollection.NAME;

@Builder
public class MongoRepositoryConfig implements RepositoryConfig {

    private final JsonConverter jsonConverter;
    private final MongoDatabase database;

    @Override
    public OtpVerificationRepository verificationRepository() {
        return MongoOtpVerificationRepository.builder()
                .verificationConverter(new OtpVerificationConverter(jsonConverter))
                .collection(database.getCollection(NAME))
                .build();
    }
}
