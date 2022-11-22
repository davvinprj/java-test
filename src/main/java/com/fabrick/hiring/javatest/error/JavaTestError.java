package com.fabrick.hiring.javatest.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JavaTestError {


    private String code;
    private String description;
    private String params;
}
