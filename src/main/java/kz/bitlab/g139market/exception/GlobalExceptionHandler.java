package kz.bitlab.g139market.exception;

import kz.bitlab.g139market.dto.HttpApiError;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<HttpApiError> handleBadRequestException(BadRequestException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(HttpApiError.builder()
                        .status(400)
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpApiError> handleGeneralException(Exception ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .internalServerError()
                .body(HttpApiError.builder()
                        .status(500)
                        .message(ex.getMessage() != null ? ex.getMessage() : "Internal Server Error")
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpApiError> handleGeneralException(NotFoundException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .internalServerError()
                .body(HttpApiError.builder()
                        .status(500)
                        .message(ex.getMessage())
                        .build());
    }
}
