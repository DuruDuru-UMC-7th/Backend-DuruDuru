package com.backend.DuruDuru.global.domain.enums;

import lombok.Getter;

@Getter
public enum StorageType {
//    ROOM("실온"),
//    FRIDGE("냉장"),
//    FROZEN("냉동");
    실온("실온"),
    냉장("냉장"),
    냉동("냉동");

    private final String storageType;

    StorageType(String storageType) {
        this.storageType = storageType;
    }
}
