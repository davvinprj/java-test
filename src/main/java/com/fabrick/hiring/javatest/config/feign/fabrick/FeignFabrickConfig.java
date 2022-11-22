package com.fabrick.hiring.javatest.config.feign.fabrick;


import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignFabrickConfig {

    @Bean
    public FabrickAuthReqeustInterceptor basicAuthRequestInterceptor() {
        return new FabrickAuthReqeustInterceptor();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FabrickErrorDecoder();
    }


}
