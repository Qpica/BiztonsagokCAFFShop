package com.Biztonsagok.CAFFShop;

import com.Biztonsagok.CAFFShop.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class BiztonsagokControllerAdvice {

    @ExceptionHandler(BiztonsagokException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(BiztonsagokException ex, WebRequest request) {
        return ResponseEntity.status(ex.getStatus()).body(new ErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception ex, WebRequest request) {
        log.error("Unknown error occurred for request: {}", request);
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO("Unknown error"));
    }

}
