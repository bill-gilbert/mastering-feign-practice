package com.example.caller.client;

import com.example.caller.controller.dto.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "calleeService", url = "${callee.url:http://localhost:8080}", fallbackFactory = CalleeFallbackFactory.class)
public interface CalleeClient {
    @GetMapping("/api/response")
    ResponseData getResponseWithMode(@RequestParam String mode);

    @GetMapping("/api/response/data")
    ResponseData getResponse(@RequestParam String mode);
}
