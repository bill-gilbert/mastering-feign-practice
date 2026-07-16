package com.example.caller.controller;

import com.example.caller.client.CalleeClient;
import com.example.caller.controller.dto.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/circuitbreaker")
@RequiredArgsConstructor
public class CallController {
    private final CalleeClient calleeClient;

    @GetMapping("/call")
    public ResponseData call() {
        return calleeClient.getResponseWithMode("mode");
    }

    @GetMapping("/call/data")
    public ResponseData callData() {
        return calleeClient.getResponseWithMode("mode");
    }
}