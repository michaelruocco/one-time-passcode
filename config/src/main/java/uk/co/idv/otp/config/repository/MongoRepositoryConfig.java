package uk.co.idv.otp.config.repository;

import com.mongodb.client.MongoDatabase;
import lombok.Builder;
import uk.co.idv.otp.adapter.repository.MongoOtpVerificationRepository;
import uk.co.idv.otp.adapter.repository.OtpVerificationConverter;
import uk.co.idv.otp.config.RepositoryConfig;
import uk.co.idv.otp.usecases.OtpVerificationRepository;
import uk.co.mruoc.json.JsonConverter;

@Builder
public class MongoRepositoryConfig implements RepositoryConfig {

    public static final String TABLE_NAME = "otp-verification";

    private final JsonConverter jsonConverter;
    private final MongoDatabase database;

    @Override
    public OtpVerificationRepository verificationRepository() {
        return MongoOtpVerificationRepository.builder()
                .verificationConverter(new OtpVerificationConverter(jsonConverter))
                .collection(database.getCollection(TABLE_NAME))
                .build();
    }
}
