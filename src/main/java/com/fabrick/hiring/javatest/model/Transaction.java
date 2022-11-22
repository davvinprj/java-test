package com.fabrick.hiring.javatest.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("operationId")
    private String operationId;

    @JsonProperty("valueDate")
    private Date valueDate;

    @JsonProperty("accountingDate")
    private Date accountingDate;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("description")
    private String description;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("transactonType")
    private TransactionType transactonType;


}
