package com.backend.DuruDuru.global.domain.enums;

import lombok.Getter;

@Getter
public enum TradeType {
    Share("나눔"),
    Exchange("교환");

    private final String type;

    TradeType(String type) {
        this.type = type;
    }
}
