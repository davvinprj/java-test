package com.fabrick.hiring.javatest.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TRANSACTION_TYPE", uniqueConstraints = { @UniqueConstraint(columnNames = { "TRANSACTION_TYPE", "TRANSACTION_BANK_TYPE" }) })
public class TransactionTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TYPE_ID")
    private Long id;

    @Column(name = "TRANSACTION_BANK_TYPE")
    private String bankTypeTransaction;

    @Column(name = "TRANSACTION_TYPE")
    private String typeTransaction;



}
