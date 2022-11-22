package com.fabrick.hiring.javatest.external.rest;


import com.fabrick.hiring.javatest.error.JavaTestError;
import com.fabrick.hiring.javatest.error.JavaTestException;
import com.fabrick.hiring.javatest.external.rest.request.FabrickMoneyTransferRequest;
import com.fabrick.hiring.javatest.external.rest.response.FabrickGetBalanceResponse;
import com.fabrick.hiring.javatest.external.rest.response.FabrickGetTransactionsResponse;
import com.fabrick.hiring.javatest.model.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountsFabrickService {

    @Autowired
    private AccountsFabrickClient accountsFabrickClient;


    public List<Transaction> getTransactions(final SearchTransactionParams searchParams) {
        log.debug("AccountsFabrickService:: getTransactions:: searchParams {}", searchParams);
        final FabrickGetTransactionsResponse fabrickGetTransactionsResponse = this.accountsFabrickClient.getTransactionsByAccountId(searchParams.getAccountId(),searchParams.getFrom() ,searchParams.getTo()).getBody();
        List<Transaction> transactions = buildTransactions(searchParams.getAccountId(),fabrickGetTransactionsResponse);
        return transactions;
    }

    public BalanceInfo getBalance(final String accountId) {
        ResponseEntity<FabrickGetBalanceResponse> fabrickGetBalanceResponse = this.accountsFabrickClient.getBalanceByAccountId(accountId);
        if (fabrickGetBalanceResponse != null && fabrickGetBalanceResponse.getBody() != null && fabrickGetBalanceResponse.getBody() != null && fabrickGetBalanceResponse.getBody().getPayload() != null) {
            FabrickGetBalanceResponse.BalanceInfo fbBalanceInfo = fabrickGetBalanceResponse.getBody().getPayload();
            return BalanceInfo.builder()
                    .availableBalance(fbBalanceInfo.getAvailableBalance())
                    .balance(fbBalanceInfo.getBalance())
                    .currency(fbBalanceInfo.getCurrency())
                    .date(fbBalanceInfo.getDate())
                    .build();
        }
        throw  JavaTestException.builder().errors(Lists.newArrayList(JavaTestError.builder().code("GET_BALANCE_ERROR").description("Error on retrieve balance").build())).build();
    }

    public void doMoneyTransfer(final MoneyTransferRequest moneyTransferRequest){

        accountsFabrickClient.postMoneyTransfer(moneyTransferRequest.getAccountId(),buildFabrickMoneyTransferReq(moneyTransferRequest));
    }

    private FabrickMoneyTransferRequest buildFabrickMoneyTransferReq(MoneyTransferRequest moneyTransferRequest){
       return FabrickMoneyTransferRequest.builder()
                .amount(moneyTransferRequest.getAmount())
                .currency(moneyTransferRequest.getCurrency())
                .description(moneyTransferRequest.getDescription())
                .creditor(FabrickMoneyTransferRequest.Creditor.builder()
                        .creditorAccount(FabrickMoneyTransferRequest.Account
                                .builder()
                                .accountCode(moneyTransferRequest.getIban()).build())
                                .creditorName(moneyTransferRequest.getReceiverName())
                        .build()).build();
    }


    private List<Transaction> buildTransactions(final String accountId, final FabrickGetTransactionsResponse fabrickGetTransactionsResponse) {
        List<Transaction> transactions = new LinkedList<>();
        if (fabrickGetTransactionsResponse != null && fabrickGetTransactionsResponse.getPayload() != null) {
            final List<FabrickGetTransactionsResponse.Transaction> fbTransactions = fabrickGetTransactionsResponse.getPayload().getTransactions();
            if (!CollectionUtils.isEmpty(fbTransactions)) {
                transactions= fbTransactions.stream().map(fbt -> {
                    Transaction transaction = Transaction.builder()
                            .accountingDate(fbt.getAccountingDate())
                            .amount(fbt.getAmount())
                            .currency(fbt.getCurrency())
                            .transactionId(fbt.getTransactionId())
                            .description(fbt.getDescription())
                            .operationId(fbt.getOperationId())
                            .valueDate(fbt.getValueDate())
                            .accountingDate(fbt.getAccountingDate())
                            .build();
                    FabrickGetTransactionsResponse.TransactionType fbTransactionType = fbt.getTransactionType();
                    if (fbTransactionType != null) {
                        transaction.setTransactonType(TransactionType.builder()
                                .bankTypeTransaction(fbTransactionType.getTypeCode())
                                .typeTransaction(fbTransactionType.getTypeValue())
                                .build());
                    }
                    return transaction;
                }).collect(Collectors.toList());
            }
        }
        return transactions;
    }
}
