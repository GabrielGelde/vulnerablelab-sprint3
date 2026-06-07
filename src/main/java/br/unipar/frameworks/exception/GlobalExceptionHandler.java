package br.unipar.frameworks.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Sprint 03 - Fase 3: envelope padronizado {status, error, path, timestamp};
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorEnvelope> handleValidation(MethodArgumentNotValidException ex,
                                                          HttpServletRequest request) {
        Map<String, String> fields = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fe -> fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "inválido",
                        (a, b) -> a
                ));
        ErrorEnvelope body = ErrorEnvelope.of(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                request.getRequestURI(),
                fields
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorEnvelope> handleResponseStatus(ResponseStatusException ex,
                                                               HttpServletRequest request) {
        int statusCode = ex.getStatusCode().value();
        // Suprimir detalhes internos para erros 5xx; manter mensagem para 4xx (user-facing)
        String message = (statusCode >= 500)
                ? "Erro interno do servidor"
                : (ex.getReason() != null ? ex.getReason() : HttpStatus.valueOf(statusCode).getReasonPhrase());

        if (statusCode >= 500) {
            log.error("Erro de servidor: {}", ex.getReason(), ex);
        }

        ErrorEnvelope body = ErrorEnvelope.of(statusCode, message, request.getRequestURI(), null);
        return ResponseEntity.status(statusCode).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorEnvelope> handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("Erro inesperado em {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        ErrorEnvelope body = ErrorEnvelope.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno",
                request.getRequestURI(),
                null
        );
        return ResponseEntity.internalServerError().body(body);
    }

    public record ErrorEnvelope(
            Instant timestamp,
            int status,
            String error,
            String path,
            Map<String, String> fields
    ) {
        static ErrorEnvelope of(int status, String error, String path, Map<String, String> fields) {
            return new ErrorEnvelope(Instant.now(), status, error, path, fields);
        }
    }
}
