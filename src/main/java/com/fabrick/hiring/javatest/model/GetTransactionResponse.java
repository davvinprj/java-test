package com.fabrick.hiring.javatest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetTransactionResponse {

    @JsonProperty("transactions")
    private List<Transaction> transactions;
}
