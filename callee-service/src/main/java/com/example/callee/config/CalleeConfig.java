package com.example.callee.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.calee")
@Getter
@Setter
public class CalleeConfig {
    private Boolean correctResponse;
    private long timeOut;
    private String responseMode;

    public void updateConfig(CalleeConfig calleeConfig) {
        this.correctResponse = calleeConfig.correctResponse;
        this.responseMode = calleeConfig.responseMode;
        this.timeOut = calleeConfig.timeOut;
    }
}
