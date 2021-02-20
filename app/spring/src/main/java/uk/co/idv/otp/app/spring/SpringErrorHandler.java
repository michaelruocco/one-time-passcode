package uk.co.idv.otp.app.spring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.badrequest.BadRequestError;
import uk.co.idv.common.adapter.json.error.internalserver.InternalServerError;
import uk.co.idv.otp.app.plain.Application;
import uk.co.mruoc.spring.filter.validation.InvalidHeaderException;

import java.util.Optional;

@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
public class SpringErrorHandler {

    private final Application application;

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> catchAll(Throwable cause) {
        Optional<ApiError> error = application.handle(cause);
        return toResponseEntity(error.orElse(toInternalServerError(cause)));
    }

    @ExceptionHandler(InvalidHeaderException.class)
    public ResponseEntity<ApiError> invalidHeader(InvalidHeaderException cause) {
        return toResponseEntity(new BadRequestError(cause.getMessage()));
    }

    private static ApiError toInternalServerError(Throwable cause) {
        log.error("unexpected error occurred", cause);
        return new InternalServerError(cause.getMessage());
    }

    private static ResponseEntity<ApiError> toResponseEntity(ApiError error) {
        return new ResponseEntity<>(error, HttpStatus.valueOf(error.getStatus()));
    }

}
