package com.example.caller.client;

import com.example.caller.controller.dto.ResponseData;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class CalleeFallbackFactory implements FallbackFactory<CalleeClient> {
    private static final Logger log = LoggerFactory.getLogger(CalleeFallbackFactory.class);

    @Override
    public CalleeClient create(Throwable cause) {
        String reason = describe(cause);
        log.warn("CalleeClient fallback: {}", reason, cause);
        return mode -> {
            log.warn("Fallback returned for mode={}", mode);
            return ResponseData.fallback(reason);
        };
    }

    private String describe(Throwable cause) {
        Throwable current = cause;
        while (current != null) {
            if (current instanceof CallNotPermittedException) {
                return "circuit breaker is OPEN; Feign did not call the gateway";
            }
            current = current.getCause();
        }
        return cause.getClass().getSimpleName() + ": " + cause.getMessage();
    }
}
