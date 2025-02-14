package com.backend.DuruDuru.global.apiPayload.exception.handler;

import com.backend.DuruDuru.global.apiPayload.code.BaseErrorCode;
import com.backend.DuruDuru.global.apiPayload.exception.GeneralException;

public class TradeHandler extends GeneralException {
    public TradeHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
