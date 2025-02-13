package com.backend.DuruDuru.global.apiPayload.exception.handler;

import com.backend.DuruDuru.global.apiPayload.code.BaseErrorCode;
import com.backend.DuruDuru.global.apiPayload.exception.GeneralException;

public class FridgeHandler extends GeneralException {
    public FridgeHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
