package com.example.caller.client;

/** A caller error (4xx) that is intentionally excluded from breaker statistics. */
public class CalleeClientException extends RuntimeException {
    public CalleeClientException(int status, String message) {
        super("HTTP " + status + ": " + message);
    }
}
