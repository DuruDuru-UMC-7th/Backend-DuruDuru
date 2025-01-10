package com.backend.DuruDuru.global.domain.enums;

import lombok.Getter;

@Getter
public enum StorageType {
    VEGETABLE("채소"),
    FRUIT("과일"),
    MEAT("육류"),
    MUSHROOM("버섯"),
    DAIRY("유제품"),
    SEAFOOD("수산물"),
    NUTS("견과류"),
    DRY("건조식품"),
    FAVORITE("기호식품");

    private final String storageType;

    StorageType(String storageType) {
        this.storageType = storageType;
    }
}
