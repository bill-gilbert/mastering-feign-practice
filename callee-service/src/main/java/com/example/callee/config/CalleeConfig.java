package com.example.callee.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.callee")
public class CalleeConfig {
    private Boolean correctResponse = true;
    private long timeOut;
    private String responseMode;

    public synchronized void updateConfig(CalleeConfig calleeConfig) {
        this.correctResponse = calleeConfig.correctResponse;
        this.responseMode = calleeConfig.responseMode;
        this.timeOut = calleeConfig.timeOut;
    }

    public Boolean getCorrectResponse() {
        return correctResponse;
    }

    public void setCorrectResponse(Boolean correctResponse) {
        this.correctResponse = correctResponse;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public String getResponseMode() {
        return responseMode;
    }

    public void setResponseMode(String responseMode) {
        this.responseMode = responseMode;
    }
}
