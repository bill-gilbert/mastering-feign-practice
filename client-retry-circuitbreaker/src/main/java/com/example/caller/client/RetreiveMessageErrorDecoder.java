package com.example.caller.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/** Converts HTTP errors to meaningful exceptions so the breaker can count server failures. */
public class RetreiveMessageErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        String message = "HTTP " + response.status();
        if (response.body() != null) {
            try (InputStream body = response.body().asInputStream()) {
                Map<String, Object> error = objectMapper.readValue(body, Map.class);
                message = String.valueOf(error.getOrDefault("message", message));
            } catch (IOException ignored) {
                // The normal Feign exception below is still useful if a proxy returns a non-JSON page.
            }
        }
        if (response.status() >= 500) {
            return new CalleeServiceException(response.status(), message);
        }
        if (response.status() >= 400) {
            return new CalleeClientException(response.status(), message);
        }
        return errorDecoder.decode(methodKey, response);
    }
}
