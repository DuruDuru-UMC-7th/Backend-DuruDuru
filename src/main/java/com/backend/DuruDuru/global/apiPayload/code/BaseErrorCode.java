package com.backend.DuruDuru.global.apiPayload.code;

public interface BaseErrorCode {
    public ErrorReasonDTO getReason();
    public ErrorReasonDTO getReasonHttpStatus();
}
