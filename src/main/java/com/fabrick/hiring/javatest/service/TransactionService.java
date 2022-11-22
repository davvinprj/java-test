package com.fabrick.hiring.javatest.service;

import com.fabrick.hiring.javatest.external.rest.AccountsFabrickService;
import com.fabrick.hiring.javatest.model.SearchTransactionParams;
import com.fabrick.hiring.javatest.model.Transaction;
import com.fabrick.hiring.javatest.model.TransactionType;
import com.fabrick.hiring.javatest.repository.SearchCacheRepository;
import com.fabrick.hiring.javatest.repository.TransactionRepository;
import com.fabrick.hiring.javatest.repository.TransactionTypeRepository;
import com.fabrick.hiring.javatest.repository.model.SearchCacheEntity;
import com.fabrick.hiring.javatest.repository.model.SearchCacheEntityPk;
import com.fabrick.hiring.javatest.repository.model.TransactionEntity;
import com.fabrick.hiring.javatest.repository.model.TransactionTypeEntity;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private SearchCacheRepository searchCacheRepository;

    @Autowired
    private AccountsFabrickService accountsFabrickService;

    @Value("${transactions.cache}")
    private boolean dbCache;


    private boolean isTransactionOnDb(String accountId, Date from, Date to) {
        return this.searchCacheRepository.findById(new SearchCacheEntityPk(accountId, from, to)).isPresent();
    }


    public List<Transaction> getTransactions(final SearchTransactionParams searchParams) {
        log.debug("-> TransactionService:: getTransactions: {} dbCache: {}", searchParams,dbCache);
        if (!dbCache) {
            return this.getTransactionsOnFabrick(searchParams);
        }

        List<Transaction> results=null;
        final boolean isOnDb = isTransactionOnDb(searchParams.getAccountId(), searchParams.getFrom(), searchParams.getTo());

        if (isOnDb) {
            List<TransactionEntity> resultsOnDb = this.getDBTransactions(searchParams.getAccountId(), searchParams.getFrom(), searchParams.getTo());
            results= mapFromDb(resultsOnDb);
        } else {
            List<Transaction> transactionsOnFabrick = accountsFabrickService.getTransactions(searchParams);
            saveSearchOnDb(searchParams.getAccountId(), searchParams.getFrom(), searchParams.getTo(), transactionsOnFabrick);
            this.searchCacheRepository.save(new SearchCacheEntity(new SearchCacheEntityPk(searchParams.getAccountId(), searchParams.getFrom(), searchParams.getTo())));
            results= transactionsOnFabrick;
        }

        log.debug("<- TransactionService:: getTransactions: {} dbCache: {}", searchParams,dbCache);
        return results;
    }

    private List<Transaction> mapFromDb(List<TransactionEntity> transactionEntities) {
        if (!CollectionUtils.isEmpty(transactionEntities)) {
            return transactionEntities.stream().map(this::mapFromDb).collect(Collectors.toList());
        }
        return Lists.newArrayList();

    }

    private Transaction mapFromDb(TransactionEntity tre) {
        return Transaction.builder().transactonType(
                TransactionType.builder()
                        .bankTypeTransaction(tre.getTransactionTypeEntity().getBankTypeTransaction())
                        .typeTransaction(tre.getTransactionTypeEntity().getTypeTransaction())
                        .build())
                .accountingDate(tre.getAccountingDate())
                .valueDate(tre.getValueDate())
                .description(tre.getDescription())
                .operationId(tre.getOperationId())
                .currency(tre.getCurrency())
                .amount(tre.getAmount())
                .transactionId(tre.getTransactionId())
                .build();
    }

    public List<Transaction> getTransactionsOnFabrick(final SearchTransactionParams searchParams) {
        return accountsFabrickService.getTransactions(searchParams);
    }

    private List<TransactionEntity> getDBTransactions(String accountId, Date fromAccountingDate, Date toAccountingDate) {
        return transactionRepository.findByAccountIdAndAccountingDateBetween(accountId, fromAccountingDate, toAccountingDate);
    }

    private void saveTransactions(@NotEmpty final String accouuntId, final List<Transaction> transactions) {
        transactions.forEach(tr -> saveTransaction(accouuntId, tr));
    }


    @Transactional
    public void saveSearchOnDb(String accountId, Date from, Date to, List<Transaction> transactions) {
        this.saveTransactions(accountId, transactions);

    }

      private TransactionEntity saveTransaction(String accountId, @NotNull Transaction transaction) {
        TransactionTypeEntity transactionTypeEntity = saveTransactionType(transaction.getTransactonType());
        Date now = new Date();
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .transactionId(transaction.getTransactionId())
                .accountId(accountId)
                .accountingDate(transaction.getAccountingDate())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .description(transaction.getDescription())
                .operationId(transaction.getOperationId())
                .updateDate(now)
                .valueDate(transaction.getValueDate())
                .transactionTypeEntity(transactionTypeEntity)
                .build();
        return transactionRepository.save(transactionEntity);
    }

    private TransactionTypeEntity saveTransactionType(@NotNull TransactionType transactionType) {

        Optional<TransactionTypeEntity> optionalTransactionTypeEntity = transactionTypeRepository.findByBankTypeTransactionAndTypeTransaction(transactionType.getBankTypeTransaction(), transactionType.getTypeTransaction());
        TransactionTypeEntity transactionTypeEntity = null;
        if (optionalTransactionTypeEntity.isPresent()) {
            transactionTypeEntity = optionalTransactionTypeEntity.get();

        } else {
            transactionTypeEntity = transactionTypeRepository.save(TransactionTypeEntity
                    .builder()
                    .bankTypeTransaction(transactionType.getBankTypeTransaction())
                    .typeTransaction(transactionType.getTypeTransaction())
                    .build());
        }
        return transactionTypeEntity;

    }
}
