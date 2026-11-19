package com.example.caller.client;

import feign.Logger;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class CalleeServiceConfiguration {
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor sourceRequestInterceptor() {
        return template -> template.header("X-Caller", "feign-circuit-breaker-lab");
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new RetreiveMessageErrorDecoder();
    }

    @Bean
    public Retryer retryer() {
        // A circuit breaker must be allowed to observe failures; retries are a separate experiment.
        return Retryer.NEVER_RETRY;
    }
}
