package com.fabrick.hiring.javatest.service;

import com.fabrick.hiring.javatest.external.rest.AccountsFabrickService;
import com.fabrick.hiring.javatest.model.BalanceInfo;
import com.fabrick.hiring.javatest.model.MoneyTransferRequest;
import com.fabrick.hiring.javatest.model.SearchTransactionParams;
import com.fabrick.hiring.javatest.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AccountService {



    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountsFabrickService accountsFabrickService;


    public BalanceInfo getBalance(final String accountId) {
        BalanceInfo balanceInfo = accountsFabrickService.getBalance(accountId);

        return balanceInfo;
    }


    public void postMoneyTransfer(final MoneyTransferRequest moneyTransferRequest) {
        accountsFabrickService.doMoneyTransfer(moneyTransferRequest);

    }


}
