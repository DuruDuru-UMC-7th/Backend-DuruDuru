package com.backend.DuruDuru.global.domain.enums;

import lombok.Getter;

@Getter
public enum TradeType {
    SHARE("나눔"),
    EXCHANGE("교환");

    private final String type;

    TradeType(String type) {
        this.type = type;
    }
}
