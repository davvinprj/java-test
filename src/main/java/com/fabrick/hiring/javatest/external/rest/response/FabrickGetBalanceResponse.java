package com.fabrick.hiring.javatest.external.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FabrickGetBalanceResponse extends FabrickResponse{

    @JsonProperty("payload")
    private BalanceInfo payload;

    @Data
    public static class BalanceInfo extends FabrickPayload{
        @JsonProperty("balance")
        private Double balance;

        @JsonProperty("availableBalance")
        private Double availableBalance;

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("date")
        private java.util.Date Date;
    }
}
