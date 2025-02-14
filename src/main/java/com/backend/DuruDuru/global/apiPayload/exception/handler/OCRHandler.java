package com.backend.DuruDuru.global.apiPayload.exception.handler;

import com.backend.DuruDuru.global.apiPayload.code.BaseErrorCode;
import com.backend.DuruDuru.global.apiPayload.exception.GeneralException;

public class OCRHandler extends GeneralException {
    public OCRHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
