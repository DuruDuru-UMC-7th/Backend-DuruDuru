package com.backend.DuruDuru.global.apiPayload.exception;

import com.backend.DuruDuru.global.apiPayload.code.BaseErrorCode;

public class RecipeException extends GeneralException {

    public RecipeException(BaseErrorCode code) {
        super(code);
    }
}
