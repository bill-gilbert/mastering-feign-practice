package com.example.caller.controller;

import com.example.caller.client.CalleeClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/call")
public class CallController {
    private final CalleeClient calleeClient;

    public CallController(CalleeClient calleeClient) {
        this.calleeClient = calleeClient;
    }

    @GetMapping
    public String call(@RequestParam(defaultValue = "ok") String mode) {
        return calleeClient.getResponse(mode);
    }
}