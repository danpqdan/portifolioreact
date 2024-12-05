package br.com.microservices.microservices.servico.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(JsonProcessingException.class)
        public ResponseEntity<Object> handleJsonProcessingException(JsonProcessingException e,
                        HttpServletRequest request) {
                ErrorDTO errorResponse = new ErrorDTO(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                e.getMessage(),
                                request.getRequestURI());

                // Retorna o erro como um JSON
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(errorResponse);
        }

        // Caso queira tratar outras exceções de forma geral
        @ExceptionHandler(Exception.class)
        public ResponseEntity<Object> handleGlobalException(Exception e, HttpServletRequest request) {
                ErrorDTO errorResponse = new ErrorDTO(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                e.getMessage(),
                                request.getRequestURI());

                // Retorna o erro como um JSON
                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(errorResponse);
        }

        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<Object> handleGlobalException(AuthenticationException e, HttpServletRequest request) {
                // Criação do objeto de erro com as informações detalhadas
                ErrorDTO errorResponse = new ErrorDTO(
                                LocalDateTime.now(),
                                HttpStatus.FORBIDDEN.value(), // Usando BAD_REQUEST em vez de INTERNAL_SERVER_ERROR
                                HttpStatus.FORBIDDEN.getReasonPhrase(),
                                e.getMessage(),
                                request.getRequestURI());

                // Retorna a resposta com o status adequado e o corpo com a estrutura de erro
                return ResponseEntity
                                .status(HttpStatus.FORBIDDEN) // Altere para BAD_REQUEST
                                .body(errorResponse);
        }

        @ExceptionHandler(UsernameNotFoundException.class)
        public ResponseEntity<Object> handleUserNotFound(UsernameNotFoundException e, HttpServletRequest request) {
                ErrorDTO errorResponse = new ErrorDTO(
                                LocalDateTime.now(),
                                HttpStatus.FORBIDDEN.value(),
                                HttpStatus.FORBIDDEN.getReasonPhrase(),
                                e.getMessage(),
                                request.getRequestURI());

                return ResponseEntity
                                .status(HttpStatus.FORBIDDEN) // Altere para BAD_REQUEST
                                .body(errorResponse);
        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<Object> handleBadCredentials(BadCredentialsException e, HttpServletRequest request) {
                // Criação do objeto de erro com as informações detalhadas
                ErrorDTO errorResponse = new ErrorDTO(
                                LocalDateTime.now(),
                                HttpStatus.FORBIDDEN.value(),
                                HttpStatus.FORBIDDEN.getReasonPhrase(),
                                e.getMessage(),
                                request.getRequestURI());

                // Retorna a resposta com o status adequado e o corpo com a estrutura de erro
                return ResponseEntity
                                .status(HttpStatus.FORBIDDEN) // Altere para BAD_REQUEST
                                .body(errorResponse);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e,
                        HttpServletRequest request) {
                // Cria um objeto de resposta de erro
                ErrorDTO errorResponse = new ErrorDTO(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                e.getMessage(),
                                request.getRequestURI());

                // Retorna o erro como um JSON
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(errorResponse);
        }

        @ExceptionHandler(SuccessResponseException.class)
        public ResponseEntity<SuccessDTO> handleSuccessResponse(SuccessResponseException e, HttpServletRequest request) {
            SuccessDTO successResponse = new SuccessDTO(
                    LocalDateTime.now(),
                    e.getStatus(),
                    e.getMessage(),
                    request.getRequestURI(),
                    e.getData()
            );
    
            return ResponseEntity.status(e.getStatus()).body(successResponse);
        }
        

        public class BadCredentialsException extends RuntimeException {
                public BadCredentialsException() {
                        super();
                }
        }
}
