package com.example.caller.controller;

import com.example.caller.client.CalleeClient;
import com.example.caller.controller.dto.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class CallController {
    private final CalleeClient calleeClient;

    public CallController(CalleeClient calleeClient) {
        this.calleeClient = calleeClient;
    }

    @GetMapping("/callee")
    public ResponseData call(@RequestParam(defaultValue = "success") String mode) {
        return calleeClient.getResponse(mode);
    }

    /** Backward-compatible aliases retained for the original exercise URLs. */
    @GetMapping({"/circuitbreaker/call", "/circuitbreaker/call/data"})
    public ResponseData callData(@RequestParam(defaultValue = "success") String mode) {
        return call(mode);
    }
}
