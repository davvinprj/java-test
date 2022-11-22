package com.fabrick.hiring.javatest.external.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
@Data
public class FabrickMoneyTransferRequest {


    @JsonProperty("creditor")
    private Creditor creditor;

    @JsonProperty("description")
    private String description;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("currency")
    private String currency;


    @Data
    @Builder
    public static class Creditor {

        @JsonProperty("name")
        private String creditorName;

        @JsonProperty("account")
        private Account creditorAccount;


    }

    @Data
    @Builder
    public static class Account {

        @JsonProperty("accountCode")
        private String accountCode;


    }
}
