package com.daniel.controller.exception;

import com.daniel.exceptions.RedisNullTokenException;
import com.daniel.exceptions.TokenMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;

/**
 * 권한 처리 예외 핸들러 클래스 <br>
 * 권한 처리 중 발생 된 예외를 처리한다.
 */
@RestControllerAdvice
public class AuthExceptionController {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> authException(AuthenticationException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exception.getMessage());
    }

    @ExceptionHandler(RedisNullTokenException.class)
    public ResponseEntity<String> nullTokenException(RedisNullTokenException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exception.getMessage());
    }

    @ExceptionHandler(TokenMismatchException.class)
    public ResponseEntity<String> tokenMissMatchException(TokenMismatchException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exception.getMessage());
    }
}