package com.backend.DuruDuru.global.apiPayload.exception;

import com.backend.DuruDuru.global.apiPayload.code.BaseErrorCode;

public class AuthException extends GeneralException {

    public AuthException(BaseErrorCode code) {
        super(code);
    }
}