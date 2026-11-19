package com.example.caller.client;

import org.springframework.cloud.openfeign.CircuitBreakerNameResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Keeps the runtime breaker name stable and readable instead of using a generated method signature.
 * The name must match resilience4j.circuitbreaker.instances.calleeService in application.yml.
 */
@Configuration(proxyBeanMethods = false)
public class CircuitBreakerConfiguration {
    @Bean
    CircuitBreakerNameResolver circuitBreakerNameResolver() {
        return (feignClientName, target, method) -> feignClientName;
    }
}
