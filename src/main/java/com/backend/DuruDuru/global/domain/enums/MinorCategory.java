package com.backend.DuruDuru.global.domain.enums;

import lombok.Getter;

@Getter
public enum MinorCategory {
    // 과일
    사과(MajorCategory.과일, StorageType.냉장, 90),
    배(MajorCategory.과일, StorageType.냉장, 21),
    포도(MajorCategory.과일, StorageType.냉장, 14),
    감(MajorCategory.과일, StorageType.냉장, 14),
    견과류(MajorCategory.과일, StorageType.실온, 365),
    베리류(MajorCategory.과일, StorageType.냉장, 5),
    감귤류(MajorCategory.과일, StorageType.실온, 21),
    과채류(MajorCategory.과일, StorageType.냉장, 5),
    망고(MajorCategory.과일, StorageType.냉장, 3),
    복숭아(MajorCategory.과일, StorageType.실온, 2),
    멜론(MajorCategory.과일, StorageType.냉장, 6),
    파인애플(MajorCategory.과일, StorageType.냉장, 5),

    // 육류
    소고기(MajorCategory.육류, StorageType.냉장, 2),
    소고기다짐육(MajorCategory.육류, StorageType.냉장, 2),
    돼지고기(MajorCategory.육류, StorageType.냉장, 2),
    닭고기(MajorCategory.육류, StorageType.냉장, 2),
    가공육(MajorCategory.육류, StorageType.냉장, 5),

    // 수산물
    담백한생선(MajorCategory.수산물, StorageType.냉장, 2),
    기름진생선(MajorCategory.수산물, StorageType.냉장, 2),
    조개류(MajorCategory.수산물, StorageType.냉장, 2),
    두족류(MajorCategory.수산물, StorageType.냉장, 2),
    갑각류(MajorCategory.수산물, StorageType.냉장, 2),
    어묵(MajorCategory.수산물, StorageType.냉장, 42),

    // 달걀
    달걀(MajorCategory.달걀, StorageType.냉장, 35),

    // 유제품
    우유(MajorCategory.유제품, StorageType.냉장, 12),
    버터(MajorCategory.유제품, StorageType.냉장, 90),
    요거트(MajorCategory.유제품, StorageType.냉장, 20),
    치즈(MajorCategory.유제품, StorageType.냉장, 120),
    아이스크림(MajorCategory.유제품, StorageType.냉동, 365),
    크림(MajorCategory.유제품, StorageType.냉장, 14),

    // 채소
    잎채소(MajorCategory.채소, StorageType.냉장, 3),
    뿌리채소(MajorCategory.채소, StorageType.냉장, 14),
    //과채류(MajorCategory.채소, StorageType.냉장, 5),
    콩류(MajorCategory.채소, StorageType.실온, 150),
    마늘(MajorCategory.채소, StorageType.냉장, 20),
    대파(MajorCategory.채소, StorageType.냉장, 14),
    양파(MajorCategory.채소, StorageType.실온, 70),
    숙주나물(MajorCategory.채소, StorageType.냉장, 4),
    콩나물(MajorCategory.채소, StorageType.냉장, 7),
    브로콜리(MajorCategory.채소, StorageType.냉장, 6),

    // 버섯
    버섯(MajorCategory.버섯, StorageType.냉장, 10),

    // 두부
    두부(MajorCategory.두부, StorageType.냉장, 20),
    가공두부(MajorCategory.두부, StorageType.냉장, 90),

    // 건조식품
    건조과일(MajorCategory.건조식품, StorageType.실온, 150),
    건미역(MajorCategory.건조식품, StorageType.실온, 365),

    // 가루
    곡물가루(MajorCategory.가루, StorageType.실온, 330),
    고춧가루(MajorCategory.가루, StorageType.냉장, 240),
    깨가루(MajorCategory.가루, StorageType.냉장, 150),
    전분(MajorCategory.가루, StorageType.실온, 365),
    콩가루(MajorCategory.가루, StorageType.실온, 330),

    // 기타
    통조림(MajorCategory.기타, StorageType.실온, 365),
    과자(MajorCategory.기타, StorageType.실온, 90),
    초콜릿(MajorCategory.기타, StorageType.실온, 90),
    과채음료(MajorCategory.기타, StorageType.냉장, 20),
    탄산음료(MajorCategory.기타, StorageType.냉장, 365),
    묵류(MajorCategory.기타, StorageType.냉장, 16),
    장류(MajorCategory.기타, StorageType.냉장, 270),
    밀떡(MajorCategory.기타, StorageType.냉동, 270),
    쌀떡(MajorCategory.기타, StorageType.냉동, 270),
    건조면류(MajorCategory.기타, StorageType.실온, 365),
    냉동면류(MajorCategory.기타, StorageType.냉동, 365),
    라면(MajorCategory.기타, StorageType.실온, 300),
    생면(MajorCategory.기타, StorageType.냉장, 12),
    소스류(MajorCategory.기타, StorageType.냉장, 180),
    조미료류(MajorCategory.기타, StorageType.실온, 90),
    기타(MajorCategory.기타, StorageType.실온, 90);




    private final MajorCategory majorCategory;
    private final StorageType storageType;
    private final int shelfLifeDays;

    MinorCategory(MajorCategory majorCategory, StorageType storageType, int shelfLifeDays) {
        this.majorCategory = majorCategory;
        this.storageType = storageType;
        this.shelfLifeDays = shelfLifeDays;
    }

    public static boolean isValidCategory(MajorCategory majorCategory, MinorCategory minorCategory) {
        return minorCategory.getMajorCategory() == majorCategory;
    }

}

