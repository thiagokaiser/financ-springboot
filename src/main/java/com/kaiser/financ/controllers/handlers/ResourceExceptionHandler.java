package com.kaiser.financ.controllers.handlers;

import com.kaiser.financ.controllers.exceptions.StandardError;
import com.kaiser.financ.controllers.exceptions.ValidationError;
import com.kaiser.financ.services.exceptions.AuthorizationException;
import com.kaiser.financ.services.exceptions.DataIntegrityException;
import com.kaiser.financ.services.exceptions.FileException;
import com.kaiser.financ.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
@ControllerAdvice
public class ResourceExceptionHandler {

  @ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
    StandardError err = new StandardError(System.currentTimeMillis(),
        HttpStatus.NOT_FOUND.value(),
        "Not Found",
        e.getMessage(),
        request.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  @ExceptionHandler(DataIntegrityException.class)
  public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
    StandardError err = new StandardError(System.currentTimeMillis(),
        HttpStatus.BAD_REQUEST.value(),
        "Bad Request",
        e.getMessage(),
        request.getRequestURI());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
    ValidationError err = new ValidationError(System.currentTimeMillis(),
        HttpStatus.UNPROCESSABLE_ENTITY.value(),
        "Unprocessable Entity",
        e.getMessage(),
        request.getRequestURI());

    for (FieldError x : e.getBindingResult().getFieldErrors()) {
      err.addError(x.getField(), x.getDefaultMessage());
    }

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
  }

  @ExceptionHandler(AuthorizationException.class)
  public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {
    StandardError err = new StandardError(System.currentTimeMillis(),
        HttpStatus.FORBIDDEN.value(),
        "Forbidden",
        e.getMessage(),
        request.getRequestURI());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
  }

  @ExceptionHandler(FileException.class)
  public ResponseEntity<StandardError> file(FileException e, HttpServletRequest request) {
    StandardError err = new StandardError(System.currentTimeMillis(),
        HttpStatus.BAD_REQUEST.value(),
        "Bad Request",
        e.getMessage(),
        request.getRequestURI());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

  // AWS SDK 2.x
  @ExceptionHandler(S3Exception.class)
  public ResponseEntity<StandardError> amazonS3(S3Exception e, HttpServletRequest request) {
    StandardError err = new StandardError(System.currentTimeMillis(),
        e.statusCode(),
        "Amazon S3",
        e.getMessage(),
        request.getRequestURI());
    return ResponseEntity.status(e.statusCode()).body(err);
  }

  @ExceptionHandler(SdkClientException.class)
  public ResponseEntity<StandardError> amazonClient(SdkClientException e, HttpServletRequest request) {
    StandardError err = new StandardError(System.currentTimeMillis(),
        HttpStatus.BAD_REQUEST.value(),
        "Amazon Client",
        e.getMessage(),
        request.getRequestURI());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<StandardError> general(Exception e, HttpServletRequest request) {
    log.error("Internal server error: ", e);
    StandardError err = new StandardError(System.currentTimeMillis(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Internal Server Error",
        e.getMessage(),
        request.getRequestURI());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
  }
}
