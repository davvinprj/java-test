package com.fabrick.hiring.javatest.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class ErrorResponse {

    @JsonProperty("code")
    private String errorCode;

    @JsonProperty("description")
    private String errorResponse;
}
