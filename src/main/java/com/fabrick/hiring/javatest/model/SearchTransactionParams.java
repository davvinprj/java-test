package com.fabrick.hiring.javatest.model;

import com.fabrick.hiring.javatest.constants.RestUtils;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@Builder
public class SearchTransactionParams {


    private String accountId;

    @DateTimeFormat(pattern = RestUtils.DATE_FORMAT)
    private Date from;

    @DateTimeFormat(pattern = RestUtils.DATE_FORMAT)
    private Date to;
}
