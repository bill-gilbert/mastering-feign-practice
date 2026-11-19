package com.example.callee.controller;

import com.example.callee.config.CalleeConfig;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/response")
public class CalleeController {
    private final CalleeConfig calleeConfig;

    public CalleeController(CalleeConfig calleeConfig) {
        this.calleeConfig = calleeConfig;
    }

    @GetMapping
    public String handle(@RequestParam(defaultValue = "success") String mode) throws InterruptedException {
        return respond(mode).data();
    }

    @GetMapping("/data")
    public ResponseData getData(@RequestParam(required = false) String mode) throws InterruptedException {
        return respond(mode == null ? calleeConfig.getResponseMode() : mode);
    }

    private ResponseData respond(String mode) throws InterruptedException {
        String actualMode = mode == null || mode.isBlank() ? "success" : mode;
        return switch (actualMode) {
            case "success" -> new ResponseData("OK");
            case "fail" -> throw new IllegalStateException("Simulated 500 error");
            case "unavailable" -> throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Simulated 503 service unavailable");
            case "slow" -> {
                Thread.sleep(calleeConfig.getTimeOut());
                yield new ResponseData("SLOW");
            }
            case "flaky" -> {
                if (Math.random() > 0.5) {
                    yield new ResponseData("FLAKY_OK");
                }
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Simulated flaky 503");
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Unknown mode: " + actualMode);
        };
    }
}
