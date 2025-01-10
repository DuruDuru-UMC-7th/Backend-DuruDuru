package com.backend.DuruDuru.global.domain.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("남"),
    FEMALE("여"),
    NoGender("없음");


    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }
}
