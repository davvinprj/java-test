package com.fabrick.hiring.javatest.external.rest;

import com.fabrick.hiring.javatest.config.feign.fabrick.FeignFabrickConfig;
import com.fabrick.hiring.javatest.constants.RestUtils;
import com.fabrick.hiring.javatest.external.rest.response.FabrickGetBalanceResponse;
import com.fabrick.hiring.javatest.external.rest.response.FabrickGetTransactionsResponse;
import com.fabrick.hiring.javatest.external.rest.response.FabrickMoneyTransferResponse;
import com.fabrick.hiring.javatest.external.rest.request.FabrickMoneyTransferRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@FeignClient(value = "fabrickClient", url = "${fabrick.api.sandbox.endpoint}/${fabrick.api.sandbox.ednpoint.gbs.accounts}", configuration = FeignFabrickConfig.class)
public interface AccountsFabrickClient {


    @RequestMapping(method = RequestMethod.GET, value = "/{accountId}/balance", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FabrickGetBalanceResponse> getBalanceByAccountId(@PathVariable("accountId") String accountId);

    @RequestMapping(method = RequestMethod.GET, value = "/{accountId}/transactions", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FabrickGetTransactionsResponse> getTransactionsByAccountId(@PathVariable("accountId") String accountId, @RequestParam("fromAccountingDate") @DateTimeFormat(pattern = RestUtils.DATE_FORMAT) Date fromAccountingDate, @RequestParam("toAccountingDate") @DateTimeFormat(pattern = RestUtils.DATE_FORMAT) Date toAccountingDate);

    @RequestMapping(method = RequestMethod.POST,value = "/{accountId}/payments/money-transfers",consumes = MediaType.APPLICATION_JSON_VALUE)
    FabrickMoneyTransferResponse postMoneyTransfer(@PathVariable("accountId") String accountId, @RequestBody FabrickMoneyTransferRequest moneyTransferRequest);
}
