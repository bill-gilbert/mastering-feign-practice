package com.example.caller.controller.dto;

/** Response returned by the caller. The last two fields reveal a fallback clearly. */
public record ResponseData(String data, boolean fallback, String reason) {
    public static ResponseData success(String data) {
        return new ResponseData(data, false, null);
    }

    public static ResponseData fallback(String reason) {
        return new ResponseData("FALLBACK", true, reason);
    }
}
