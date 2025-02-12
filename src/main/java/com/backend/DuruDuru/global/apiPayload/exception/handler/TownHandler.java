package com.backend.DuruDuru.global.apiPayload.exception.handler;

import com.backend.DuruDuru.global.apiPayload.code.BaseErrorCode;
import com.backend.DuruDuru.global.apiPayload.exception.GeneralException;

public class TownHandler extends GeneralException {
    public TownHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
