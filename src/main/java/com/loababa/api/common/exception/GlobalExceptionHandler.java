package com.loababa.api.common.exception;

import com.loababa.api.common.model.ApiResponse;
import com.loababa.api.common.service.impl.MessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSender messageSender;

    // DEFAULT

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<Void> handleRuntimeException(RuntimeException e) {
        messageSender.sendErrorNotification("오류가 발생했습니다: " + e.getMessage());
        log.error(e.getMessage(), e);
        return ApiResponse.fail("NOT_DEFINED_ERROR", "관리자에게 문의하세요.");
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.debug(e.getMessage(), e);
        String defaultMessage = e.getBindingResult()
                .getAllErrors()
                .getFirst().getDefaultMessage();
        return ApiResponse.fail("VALIDATION_FAILED", defaultMessage);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ApiResponse<Void> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        log.debug(e.getMessage(), e);
        return ApiResponse.fail(
                "VALIDATION_FAILED",
                e.getAllValidationResults()
                        .getFirst()
                        .getResolvableErrors()
                        .getFirst()
                        .getDefaultMessage()
        );
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.debug(e.getMessage(), e);
        return ApiResponse.fail("MALFORMED_JSON_REQUEST", "올바르지 않은 값입니다.");
    }

    // 4XX

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(LoababaBadRequestException.class)
    public ApiResponse<Void> handleLoababaBadRequestException(LoababaBadRequestException e) {
        log.debug(e.getMessage(), e);
        return ApiResponse.fail(e.getClientCode(), e.getClientMessage());
    }

    // 5XX

    @ResponseStatus(BAD_GATEWAY)
    @ExceptionHandler(LoababaBadGatewayException.class)
    public ApiResponse<Void> handleLoababaBadGateWayException(LoababaBadGatewayException e) {
        log.debug(e.getMessage(), e);
        return ApiResponse.fail(e.getClientCode(), e.getClientMessage());
    }

}
