package com.example.callee.controller;

import com.example.callee.config.CalleeConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
public class ConfigController {
    private final CalleeConfig calleeConfiguration;

    @GetMapping
    public CalleeConfig getConfig() {
        return calleeConfiguration;
    }

    @PostMapping
    public CalleeConfig updateConfig(@RequestBody CalleeConfig calleeConfig) {
        calleeConfiguration.updateConfig(calleeConfig);
        return calleeConfiguration;
    }

}
