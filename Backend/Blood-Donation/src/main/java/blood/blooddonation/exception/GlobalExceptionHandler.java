package blood.blooddonation.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(
                Map.of(
                        "error", "VALIDATION_ERROR",
                        "fields", errors
                )
        );
    }

    // email exists
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailExists(EmailAlreadyExistsException ex) {

        return ResponseEntity.status(409).body(
                Map.of(
                        "error", "EMAIL_ALREADY_EXISTS",
                        "message", ex.getMessage()
                )
        );
    }

    // user not found
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {

        return ResponseEntity.status(404).body(
                Map.of(
                        "error", "USER_NOT_FOUND",
                        "message", ex.getMessage()
                )
        );
    }

    // fallback (any unknown error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {

        return ResponseEntity.status(500).body(
                Map.of(
                        "error", "SERVER_ERROR",
                        "message", "Something went wrong"
                )
        );
    }

    // invalid login
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentials(InvalidCredentialsException ex) {

        return ResponseEntity.status(401).body(
                Map.of(
                        "error", "INVALID_CREDENTIALS",
                        "message", ex.getMessage()
                )
        );
    }

    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials() {
        return ResponseEntity
                .status(401)
                .body(Map.of("error", "Invalid email or password"));
    }

    @ExceptionHandler(org.springframework.security.core.userdetails.UsernameNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound() {
        return ResponseEntity
                .status(401)
                .body(Map.of("error", "Invalid email or password"));
    }

}