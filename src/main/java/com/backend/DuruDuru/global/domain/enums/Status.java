package com.backend.DuruDuru.global.domain.enums;

import lombok.Getter;

@Getter
public enum Status {
    COMPLETE("완료"),
    PROCEEDING("진행중"),
    ACTIVE("등록");

    private final String status;

    Status(String status) {
        this.status = status;
    }
}
