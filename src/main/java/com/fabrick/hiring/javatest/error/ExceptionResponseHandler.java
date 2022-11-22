package com.fabrick.hiring.javatest.error;

import com.fabrick.hiring.javatest.constants.ErrorCode;
import com.fabrick.hiring.javatest.constants.ResponseUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;


@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class ExceptionResponseHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        if (e instanceof JavaTestException) {
            JavaTestException javaTestException = (JavaTestException) e;
            List<JavaTestError> javaTestErrors = javaTestException.getErrors();
            log.error("errors: {}", javaTestErrors);
            return ResponseUtil.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),buildErrorResponse(javaTestException));
        }

        if(e instanceof MissingRequestValueException || e instanceof MethodArgumentTypeMismatchException){

            return ResponseUtil.getErrorResponse(HttpStatus.BAD_REQUEST.value(), ErrorResponse.builder().errorCode(ErrorCode.INVALID_INPUT.name()).errorResponse(e.getMessage()).build());
        }
        if(e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException ex =  (MethodArgumentNotValidException)e;
            log.debug("param {}", ex.getParameter());
            String errorMessage="";

            FieldError fieldError = ex.getBindingResult().getFieldError();
            if(fieldError!=null){
                errorMessage=String.format("Field %s %s", fieldError.getField(), fieldError.getDefaultMessage());
            }

            return ResponseUtil.getErrorResponse(HttpStatus.BAD_REQUEST.value(), ErrorResponse.builder().errorCode(ErrorCode.INVALID_INPUT.name()).errorResponse(errorMessage).build());

        }

        return ResponseUtil.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorResponse.builder().errorCode("GENERIC_ERROR").errorResponse(e.getMessage()).build());

    }


    private ErrorResponse buildErrorResponse(JavaTestException ex) {
        if (ex != null) {
            List<JavaTestError> javaTestErrors = ex.getErrors();
            if (!CollectionUtils.isEmpty(javaTestErrors)) {
                JavaTestError error = javaTestErrors.get(0);
                return ErrorResponse.builder().errorCode(error.getCode()).errorResponse(error.getDescription()).build();
            }
        }
        return ErrorResponse.builder().errorCode("GENERIC_ERROR").errorResponse("Generic Error. Contact Support.").build();
    }


}
