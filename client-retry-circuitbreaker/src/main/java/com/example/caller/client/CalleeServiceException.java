package com.example.caller.client;

/** An upstream 5xx response. This is a failure that the circuit breaker must count. */
public class CalleeServiceException extends RuntimeException {
    public CalleeServiceException(int status, String message) {
        super("HTTP " + status + ": " + message);
    }
}
