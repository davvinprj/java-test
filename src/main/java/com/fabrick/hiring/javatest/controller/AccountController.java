package com.fabrick.hiring.javatest.controller;

import com.fabrick.hiring.javatest.constants.ResponseUtil;
import com.fabrick.hiring.javatest.constants.RestUtils;
import com.fabrick.hiring.javatest.model.*;
import com.fabrick.hiring.javatest.service.AccountService;
import com.fabrick.hiring.javatest.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController("/account")
public class AccountController {


    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{accountId}/balance")
    public ResponseEntity getBalance(@PathVariable(value = "accountId") String accountId) {
        log.info("-> AccountController::getBalance:: accountId: {}", accountId);

        final BalanceInfo balanceInfo = accountService.getBalance(accountId);
        log.info("<- getBalance:: accountId: {} balance: {}", accountId, balanceInfo);
        return ResponseEntity.ok(balanceInfo);
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity getTransactions(@PathVariable(value = "accountId") String accountId, @Valid
    @RequestParam("fromDate") @DateTimeFormat(pattern = RestUtils.DATE_FORMAT) Date from, @Valid
                                          @RequestParam("toDate") @DateTimeFormat(pattern = RestUtils.DATE_FORMAT) Date to) {
        log.info("-> getTransactions:: accountId; {} from: {} to: {}", accountId, from, to);
        final SearchTransactionParams searchTransactionParams = SearchTransactionParams.builder().accountId(accountId).from(from).to(to).build();
        List<Transaction> transactions = transactionService.getTransactions(searchTransactionParams);

        log.debug("getTransactions:: Transactions: {}", transactions);
        log.info("<- getTransactions:: accountId: {} from: {} to: {} num transactions: {}", accountId, from, to, transactions.size());
        return ResponseEntity.ok(GetTransactionResponse.builder().transactions(transactions).build());
    }

    @PostMapping("/{accountId}/payments/money-transfer")
    public ResponseEntity doMoneyTransfer(@PathVariable(value = "accountId") String accountId, @Valid @RequestBody MoneyTransferRequest moneyTransferRequest) {
        log.info("-> AccountController::doMoneyTransfer:: accountId: {} moneyTransferRequest: {}", accountId, moneyTransferRequest);
        moneyTransferRequest.setAccountId(accountId);
        accountService.postMoneyTransfer(moneyTransferRequest);
        log.info("<- AccountController::doMoneyTransfer:: accountId: {} moneyTransferRequest: {}", accountId, moneyTransferRequest);
        return ResponseUtil.getOkResponse();
    }

}