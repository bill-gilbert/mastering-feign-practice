package com.example.callee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/response")
public class CalleeController {

    @GetMapping
    public String handle(@RequestParam(defaultValue = "ok") String mode) throws InterruptedException {
        return switch (mode) {
            case "ok" -> "OK: " + System.currentTimeMillis();
            case "fail" -> throw new RuntimeException("Simulated 500 Error");
            case "slow" -> {
                Thread.sleep(4000); // > readTimeout (2500ms)
                yield "SLOW: " + System.currentTimeMillis();
            }
            case "flaky" -> {
                if (Math.random() > 0.5) {
                    yield "FLAKY_OK: " + System.currentTimeMillis();
                } else {
                    throw new RuntimeException("Flaky 503");
                }
            }
            default -> "UNKNOWN_MODE";
        };
    }
}