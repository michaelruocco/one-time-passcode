package uk.co.idv.otp.app.spring;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.badrequest.BadRequestError;
import uk.co.idv.otp.app.manual.Application;
import uk.co.mruoc.spring.filter.validation.InvalidHeaderException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SpringErrorHandlerTest {

    private final Application application = mock(Application.class);

    private final SpringErrorHandler handler = new SpringErrorHandler(application);

    @Test
    void shouldConvertThrowableToResponseEntityWithError() {
        HttpStatus status = HttpStatus.CREATED;
        Throwable cause = new Throwable();
        givenConvertedToErrorWithStatus(cause, status);

        ResponseEntity<ApiError> response = handler.catchAll(cause);

        assertThat(response.getStatusCode()).isEqualTo(status);
    }

    @Test
    void shouldConvertInvalidHeaderExceptionToResponseEntityWithError() {
        InvalidHeaderException cause = new InvalidHeaderException("error-message");

        ResponseEntity<ApiError> response = handler.invalidHeader(cause);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(new BadRequestError(cause.getMessage()));
    }

    private void givenConvertedToErrorWithStatus(Throwable cause, HttpStatus status) {
        ApiError error = mock(ApiError.class);
        given(error.getStatus()).willReturn(status.value());
        given(application.handle(cause)).willReturn(Optional.of(error));
    }

}
