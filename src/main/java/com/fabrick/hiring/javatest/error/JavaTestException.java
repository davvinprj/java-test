package com.fabrick.hiring.javatest.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class JavaTestException extends RuntimeException {


    private List<JavaTestError> errors;
}
