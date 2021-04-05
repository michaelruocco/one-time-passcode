package uk.co.idv.otp.app.spring.config.security.fake;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class FakeJwtDecoderTest {

    private final FakeJwtValidator validator = mock(FakeJwtValidator.class);
    private final FakeJwtFactory factory = mock(FakeJwtFactory.class);

    private final FakeJwtDecoder decoder = new FakeJwtDecoder(validator, factory);

    @Test
    void shouldValidateToken() {
        String token = "my-token";

        decoder.decode(token);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(validator).validate(captor.capture());
        assertThat(captor.getValue()).isEqualTo(token);
    }

    @Test
    void shouldReturnJwt() {
        String token = "my-token";
        Jwt expectedJwt = mock(Jwt.class);
        given(factory.toJwt(token)).willReturn(expectedJwt);

        Jwt jwt = decoder.decode(token);

        assertThat(jwt).isEqualTo(expectedJwt);
    }

}
