package com.fabrick.hiring.javatest.config.feign.fabrick;

import com.fabrick.hiring.javatest.error.JavaTestError;
import com.fabrick.hiring.javatest.error.JavaTestException;
import com.fabrick.hiring.javatest.external.rest.response.FabrickError;
import com.fabrick.hiring.javatest.external.rest.response.FabrickResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class FabrickErrorDecoder implements ErrorDecoder {


    @Override
    public Exception decode(String methodKey, Response response) {

        final HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        log.info("Error on call " + methodKey + " with response " + responseStatus);
        FabrickResponse fabrickResponse = this.getFabrickResponse(response);

        if(fabrickResponse!=null){
            Arrays.stream(fabrickResponse.getErrors()).map(fr-> new JavaTestError(fr.getCode(),fr.getDescription(),fr.getParams())).collect(Collectors.toList());
            return JavaTestException.builder().errors(Arrays.stream(fabrickResponse.getErrors()).map(fr-> new JavaTestError(fr.getCode(),fr.getDescription(),fr.getParams())).collect(Collectors.toList())).build();
        }
        return JavaTestException.builder().errors(Lists.newArrayList(new JavaTestError("GENERIC_ERROR", "Fabrick response not found",null))).build();
    }

    private FabrickResponse getFabrickResponse(Response response){
        FabrickResponse fabrickResponse = null;
        if(response!=null && response.body()!=null){
            try (InputStream bodyIs = response.body()
                    .asInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                fabrickResponse = mapper.readValue(bodyIs, FabrickResponse.class);
                log.debug("fabrick response {}", fabrickResponse);
                return fabrickResponse;
            } catch (IOException e) {
                log.error(e.getMessage());
                JavaTestError error = new JavaTestError("GENERIC_ERROR", e.getMessage(),null);
                throw new JavaTestException(Lists.newArrayList(error));
            }
        }
        return fabrickResponse;
    }
}
