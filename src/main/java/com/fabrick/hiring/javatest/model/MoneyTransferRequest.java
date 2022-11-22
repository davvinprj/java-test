package com.fabrick.hiring.javatest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NotNull(message = "Money Transfer Request empty")
public class MoneyTransferRequest {


    @JsonIgnore
    private String accountId;

    @NotEmpty
    @JsonProperty("iban")
    private String iban;

    @NotEmpty
    @JsonProperty("receiverName")
    private String receiverName;

    @NotEmpty
    @JsonProperty("description")
    private String description;

    @NotEmpty
    @JsonProperty("currency")
    private String currency;

    @NotNull
    @JsonProperty("amount")
    private Double amount;
}
