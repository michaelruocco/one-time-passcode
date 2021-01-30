package uk.co.idv.otp.app.spring.info;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.info.Info;

import static org.assertj.core.api.Assertions.assertThat;

class SystemPropertyInfoContributorTest {

    private final SystemPropertyInfoContributor contributor = new SystemPropertyInfoContributor();

    @Test
    void shouldPopulateAllSystemPropertiesOnInfo() {
        Info.Builder builder = new Info.Builder();

        contributor.contribute(builder);

        Info info = builder.build();
        assertThat(info.get("systemProperties")).isEqualTo(System.getProperties());
    }

}
