package com.fabrick.hiring.javatest.config.feign.fabrick;

import com.fabrick.hiring.javatest.constants.RestUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;



public class FabrickAuthReqeustInterceptor implements RequestInterceptor {

    @Value("${fabrick.api.sandbox.key}")
    private String apiKey;

    @Value("${fabrick.api.sandbox.auth-schema}")
    private String authSchema;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(RestUtils.FABRICK_ATUH_SCHEMA_HEADER,authSchema);
        requestTemplate.header(RestUtils.FABRICK_API_KEY_HEADER,apiKey);

    }
}
