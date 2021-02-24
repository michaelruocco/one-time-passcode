package uk.co.idv.otp.config.repository;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.conversions.Bson;

import static java.util.concurrent.TimeUnit.SECONDS;
import static uk.co.idv.otp.adapter.repository.OtpVerificationCollection.TTL_INDEX_NAME;
import static uk.co.idv.otp.adapter.repository.OtpVerificationCollection.NAME;

@ChangeLog
public class MongoOtpVerificationChangeLog {

    @ChangeSet(order = "001", id = "create-otp-verification-collection", author = "system")
    public void createCollection(MongoDatabase database) {
        database.createCollection(NAME);
        database.getCollection(NAME).createIndex(indexKeys(), indexOptions());
    }

    private Bson indexKeys() {
        return Indexes.ascending(TTL_INDEX_NAME);
    }

    private IndexOptions indexOptions() {
        return new IndexOptions().expireAfter(60L, SECONDS);
    }

}
