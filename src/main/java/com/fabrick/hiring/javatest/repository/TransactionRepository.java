package com.fabrick.hiring.javatest.repository;

import com.fabrick.hiring.javatest.repository.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

    List<TransactionEntity> findByAccountIdAndAccountingDateBetween(String accountId, Date from, Date to);

}
