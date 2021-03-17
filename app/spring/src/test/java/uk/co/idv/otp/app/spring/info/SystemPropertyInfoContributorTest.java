package uk.co.idv.otp.app.spring.info;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.info.Info;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SystemPropertyInfoContributorTest {

    private final SecureProperties secureProperties = mock(SecureProperties.class);

    private final SystemPropertyInfoContributor contributor = new SystemPropertyInfoContributor(secureProperties);

    @Test
    void shouldPopulateAllSystemPropertiesOnInfo() {
        Info.Builder builder = new Info.Builder();
        Properties systemProperties = System.getProperties();
        Properties securedProperties = givenSecuredProperties(systemProperties);

        contributor.contribute(builder);

        Info info = builder.build();
        assertThat(info.get("systemProperties")).isEqualTo(securedProperties);
    }

    private Properties givenSecuredProperties(Properties original) {
        Properties secured = new Properties();
        given(secureProperties.apply(original)).willReturn(secured);
        return secured;
    }

}
