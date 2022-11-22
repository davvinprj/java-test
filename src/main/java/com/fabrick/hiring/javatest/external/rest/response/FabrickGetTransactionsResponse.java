package com.fabrick.hiring.javatest.external.rest.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FabrickGetTransactionsResponse extends FabrickResponse {

    @JsonProperty("payload")
    private Transactions payload;

    @Data
    public static class Transactions extends FabrickPayload{
        @JsonProperty("list")
        private List<Transaction> transactions;
    }


    @Data
    public static class Transaction{

        @JsonProperty("transactionId")
        private String transactionId;

        @JsonProperty("operationId")
        private String operationId;

        @JsonProperty("accountingDate")
        private Date accountingDate;

        @JsonProperty("valueDate")
        private Date valueDate;

        @JsonProperty("type")
        private TransactionType transactionType;

        @JsonProperty("amount")
        private Double amount;

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("description")
        private String description;

    }

    @Data
    public static class TransactionType{

        @JsonProperty("enumeration")
        private String typeCode;

        @JsonProperty("value")
        private String typeValue;
    }

}
