package com.example.callee.controller;

import com.example.callee.config.CalleeConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/response")
@RequiredArgsConstructor
public class CalleeController {
    private final CalleeConfig calleeConfig;

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

    @GetMapping("/data")
    public ResponseData getData() throws InterruptedException {

        return switch (calleeConfig.getResponseMode()) {
            case "success" -> new ResponseData("OK");
            case "fail" -> throw new RuntimeException("Simulated 500 Error");
            case "slow" -> {
                Thread.sleep(calleeConfig.getTimeOut()); // > readTimeout (2500ms)
                yield new ResponseData("Slow");
            }
            case "flaky" -> {
                if (Math.random() > 0.5) {
                    yield new ResponseData("FlakyOk");
                } else {
                    throw new RuntimeException("Flaky 503");
                }
            }
            default -> new ResponseData("UnknownMode");
        };
    }
}
