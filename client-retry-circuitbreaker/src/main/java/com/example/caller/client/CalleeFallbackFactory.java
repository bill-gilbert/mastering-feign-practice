package com.example.caller.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class CalleeFallbackFactory implements FallbackFactory<CalleeClient> {
    private static final Logger log = LoggerFactory.getLogger(CalleeFallbackFactory.class);

    @Override
    public CalleeClient create(Throwable cause) {
        return mode -> {
            log.error("CB/Retry fallback triggered for mode='{}'. Reason: {}", mode, cause.getMessage());
            return "FALLBACK: Service protected. Cause: " + cause.getClass().getSimpleName();
        };
    }
}