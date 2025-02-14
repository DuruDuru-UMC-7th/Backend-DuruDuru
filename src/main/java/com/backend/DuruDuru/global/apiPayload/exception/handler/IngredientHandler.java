package com.backend.DuruDuru.global.apiPayload.exception.handler;

import com.backend.DuruDuru.global.apiPayload.code.BaseErrorCode;
import com.backend.DuruDuru.global.apiPayload.code.ErrorReasonDTO;
import com.backend.DuruDuru.global.apiPayload.code.status.ErrorStatus;
import com.backend.DuruDuru.global.apiPayload.exception.GeneralException;

public class IngredientHandler extends GeneralException {
    public IngredientHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}


