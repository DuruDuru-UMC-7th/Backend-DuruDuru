package com.backend.DuruDuru.global.domain.enums;

import lombok.Getter;

@Getter
public enum NotificationType {
    TRADE("품앗이"),
    CHAT("채팅"),
    INGREDIENT("식재료");

    private final String notificationType;
    NotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
}
