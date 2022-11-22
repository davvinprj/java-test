package com.fabrick.hiring.javatest.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCacheEntityPk implements Serializable {


    @Column(name = "ACCOUNT_ID")
    private String accountId;


    @Column(name = "FROM_DATE")
    private Date from;


    @Column(name = "TO_DATE")
    private Date to;
}
