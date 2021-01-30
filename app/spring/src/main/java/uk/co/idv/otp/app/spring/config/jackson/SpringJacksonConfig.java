package uk.co.idv.otp.app.spring.config.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.otp.adapter.json.OtpAppModule;
import uk.co.mruoc.json.JsonConverter;
import uk.co.mruoc.json.jackson.JacksonJsonConverter;

@Configuration
public class SpringJacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return ObjectMapperFactory.build(new OtpAppModule());
    }

    @Bean
    public JsonConverter jsonConverter(ObjectMapper mapper) {
        return new JacksonJsonConverter(mapper);
    }

}
