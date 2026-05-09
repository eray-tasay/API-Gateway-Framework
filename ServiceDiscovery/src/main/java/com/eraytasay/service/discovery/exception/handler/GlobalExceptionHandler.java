package com.eraytasay.service.discovery.exception.handler;

import com.eraytasay.service.discovery.dto.response.ResponseDto;
import com.eraytasay.service.discovery.dto.response.ResponseType;
import com.eraytasay.service.discovery.exception.NoSuchServiceException;
import com.eraytasay.service.discovery.exception.ServiceAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ServiceAlreadyExistsException.class)
    public ResponseEntity<ResponseDto<Void>> handleServiceAlreadyExistsExc(ServiceAlreadyExistsException ex)
    {
        return handleBadRequest(ex);
    }

    @ExceptionHandler(NoSuchServiceException.class)
    public ResponseEntity<ResponseDto<Void>> handleNoSuchServiceExc(NoSuchServiceException ex)
    {
        return handleBadRequest(ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Void>> handleExc(Exception ex)
    {
        log.error(ex.getMessage());

        var response = new ResponseDto<Void>(
                "An unexpected error occurred.",
                ResponseType.ERROR
        );

        return ResponseEntity.internalServerError().body(response);
    }

    private ResponseEntity<ResponseDto<Void>> handleBadRequest(Exception ex)
    {
        log.error(ex.getMessage());

        var response = new ResponseDto<Void>(
                ex.getMessage(),
                ResponseType.ERROR
        );

        return ResponseEntity.badRequest().body(response);
    }
}
