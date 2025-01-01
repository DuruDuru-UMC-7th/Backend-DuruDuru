package com.backend.DuruDuru.global.apiPayload.exception.handler;

import com.backend.DuruDuru.global.apiPayload.code.BaseErrorCode;
import com.backend.DuruDuru.global.apiPayload.exception.GeneralException;

public class MemberHandler extends GeneralException {
    public MemberHandler(BaseErrorCode baseErrorCode){
        super(baseErrorCode);
    }
}
