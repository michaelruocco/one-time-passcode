package uk.co.idv.otp.app.spring.config.repository;

import com.github.mongobee.Mongobee;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.otp.config.repository.MongoOtpVerificationChangeLog;
import uk.co.idv.otp.config.repository.MongoRepositoryConfig;
import uk.co.idv.otp.usecases.OtpVerificationRepository;
import uk.co.mruoc.json.JsonConverter;

@Configuration
@Profile("!stubbed")
public class SpringMongoRepositoryConfig {

    @Bean
    public OtpVerificationRepository verificationRepository(JsonConverter jsonConverter, MongoDatabase database) {
        return MongoRepositoryConfig.builder()
                .jsonConverter(jsonConverter)
                .database(database)
                .build()
                .verificationRepository();
    }

    @Bean
    public Mongobee verificationMongobee(){
        Mongobee runner = new Mongobee(loadConnectionString());
        runner.setChangeLogsScanPackage(MongoOtpVerificationChangeLog.class.getPackageName());
        return runner;
    }

    @Bean
    public MongoClient mongoClient() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(loadConnectionString()))
                .build();
        return MongoClients.create(settings);
    }

    @Bean
    public MongoDatabase mongoDatabase(MongoClient client) {
        return client.getDatabase(extractDatabaseName(loadConnectionString()));
    }

    private static String extractDatabaseName(String connectionString) {
        String[] tokens = connectionString.split("/");
        return tokens[tokens.length - 1];
    }

    private static String loadConnectionString() {
        return System.getProperty("spring.data.mongodb.uri");
    }

}
