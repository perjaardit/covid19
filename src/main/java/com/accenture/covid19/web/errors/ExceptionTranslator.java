package com.accenture.covid19.web.errors;

import com.accenture.covid19.dto.response.CorrelationResponseDTO;
import com.accenture.covid19.exception.GeneralCovidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler(GeneralCovidException.class)
    public ResponseEntity<CorrelationResponseDTO> handleCustomServerException(final GeneralCovidException ex) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CorrelationResponseDTO.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CorrelationResponseDTO> handleByDefault(final Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CorrelationResponseDTO.builder().message(GeneralCovidException.INTERNAL_ERROR_MESSAGE).build());
    }

}
