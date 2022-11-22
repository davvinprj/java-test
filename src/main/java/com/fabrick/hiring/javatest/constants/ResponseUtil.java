package com.fabrick.hiring.javatest.constants;

import com.fabrick.hiring.javatest.error.ErrorResponse;
import com.fabrick.hiring.javatest.error.JavaTestError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {


    public static ResponseEntity getErrorResponse(final int status, final ErrorResponse errorResponse){
        return ResponseEntity.status(status).body(errorResponse);
    }

    public static ResponseEntity getOkResponse(){
        return ResponseEntity.status(HttpStatus.OK).body("\"status\": \"OK\"");
    }
}
