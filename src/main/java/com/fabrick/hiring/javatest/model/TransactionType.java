package com.fabrick.hiring.javatest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class TransactionType implements Serializable {

    @JsonProperty("bankTypeTransaction")
    private String bankTypeTransaction;

    @JsonProperty("typeTransaction")
    private String typeTransaction;


}
