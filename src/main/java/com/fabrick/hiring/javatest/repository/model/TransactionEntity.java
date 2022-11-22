package com.fabrick.hiring.javatest.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TRANSACTION")
public class TransactionEntity {

    @Id
    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    @Column(name = "OPERATION_ID")
    private String operationId;

    @Column(name = "ACCOUNT_ID")
    private String accountId;

    @Column(name = "VALUE_DATE")
    private Date valueDate;

    @Column(name = "ACCOUNTING_DATE")
    private Date accountingDate;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CURRENCY")
    private String currency;

    @ManyToOne
    private TransactionTypeEntity transactionTypeEntity;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;


}
