package com.backend.DuruDuru.global.domain.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("M"),
    FEMALE("F"),
    NoGender("U");


    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public static Gender fromString(String genderStr) {
        for (Gender gender: Gender.values()) {
            if (gender.gender.equals(genderStr)) {
                return gender;
            }
        }
        return NoGender;
    }
}
