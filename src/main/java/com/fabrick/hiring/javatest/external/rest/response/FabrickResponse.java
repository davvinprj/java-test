package com.fabrick.hiring.javatest.external.rest.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public  class FabrickResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("errors")
    private FabrickError[] errors;

    @JsonIgnore
    private Object payload;

}

