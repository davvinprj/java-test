package com.fabrick.hiring.javatest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class BalanceInfo {

    @JsonProperty("balance")
    private Double balance;

    @JsonProperty("availableBalance")
    private Double availableBalance;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("date")
    private Date date;
}
