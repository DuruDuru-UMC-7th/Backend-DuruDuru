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

    // Enum 값 변환 메서드
    public static StorageType fromString(String value) {
        for (StorageType type : StorageType.values()) {
            if (type.getStorageType().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("올바르지 않은 보관방식입니다: " + value);
    }

//    StorageType(String storageType) {
//        this.storageType = storageType;
//    }
}
