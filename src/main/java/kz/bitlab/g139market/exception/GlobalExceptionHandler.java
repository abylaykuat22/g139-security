package kz.bitlab.g139market.exception;

import kz.bitlab.g139market.dto.HttpApiError;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

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

    @ExceptionHandler(NotExistsOnStockException.class)
    public ResponseEntity<HttpApiError> handleNotExistsOnStockException(NotExistsOnStockException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .internalServerError()
                .body(HttpApiError.builder()
                        .status(500)
                        .message("Internal Server Error")
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(HttpApiError.builder()
                        .status(400)
                        .message(ex.getAllErrors().stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.joining(", ")))
                        .build());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<HttpApiError> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(HttpApiError.builder()
                        .status(403)
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpApiError> handleGeneralException(NotFoundException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(HttpApiError.builder()
                        .status(500)
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<HttpApiError> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(HttpApiError.builder()
                        .status(400)
                        .message(ex.getMessage())
                        .build());
    }
}
