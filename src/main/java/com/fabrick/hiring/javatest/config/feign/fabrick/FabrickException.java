package com.fabrick.hiring.javatest.config.feign.fabrick;

import com.fabrick.hiring.javatest.external.rest.response.FabrickError;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FabrickException extends Exception {


    private String status;
    private List<FabrickError> errors;

}
