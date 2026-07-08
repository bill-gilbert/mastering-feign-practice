package com.example.caller.controller;

import com.example.caller.client.CalleeClient;
import com.example.caller.controller.dto.ResponseData;
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
    public ResponseData call() {
        return calleeClient.getResponseWithMode();
    }
}
