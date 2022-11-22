package com.fabrick.hiring.javatest.repository;

import com.fabrick.hiring.javatest.model.TransactionType;
import com.fabrick.hiring.javatest.repository.model.TransactionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionTypeRepository extends JpaRepository<TransactionTypeEntity,Long> {
    Optional<TransactionTypeEntity> findByBankTypeTransactionAndTypeTransaction (String bankTypeTransaction, String typeTransaction);
}
