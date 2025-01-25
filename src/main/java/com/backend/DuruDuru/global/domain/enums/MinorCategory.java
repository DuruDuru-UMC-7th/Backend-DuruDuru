package com.backend.DuruDuru.global.domain.enums;

import lombok.Getter;

@Getter
public enum MinorCategory {
    // 과일
    사과(MajorCategory.과일),
    배(MajorCategory.과일),
    포도(MajorCategory.과일),
    감(MajorCategory.과일),
    견과류(MajorCategory.과일),
    베리류(MajorCategory.과일),
    감귤류(MajorCategory.과일),
    과채류(MajorCategory.과일),
    망고(MajorCategory.과일),
    복숭아(MajorCategory.과일),
    멜론(MajorCategory.과일),
    파인애플(MajorCategory.과일),

    // 육류
    소고기(MajorCategory.육류),
    소고기다짐육(MajorCategory.육류),
    돼지고기(MajorCategory.육류),
    닭고기(MajorCategory.육류),
    가공육(MajorCategory.육류),

    // 수산물
    담백한생선(MajorCategory.수산물),
    기름진생선(MajorCategory.수산물),
    조개류(MajorCategory.수산물),
    두족류(MajorCategory.수산물),
    갑각류(MajorCategory.수산물),
    어묵(MajorCategory.수산물),

    // 달걀
    달걀(MajorCategory.달걀),

    // 유제품
    우유(MajorCategory.유제품),
    버터(MajorCategory.유제품),
    요거트(MajorCategory.유제품),
    치즈(MajorCategory.유제품),
    아이스크림(MajorCategory.유제품),
    크림(MajorCategory.유제품),

    // 채소
    잎채소(MajorCategory.채소),
    뿌리채소(MajorCategory.채소),
    콩류(MajorCategory.채소),
    마늘(MajorCategory.채소),
    대파(MajorCategory.채소),
    양파(MajorCategory.채소),
    숙주나물(MajorCategory.채소),
    콩나물(MajorCategory.채소),
    브로콜리(MajorCategory.채소),

    // 버섯
    버섯(MajorCategory.버섯),

    // 두부
    두부(MajorCategory.두부),
    가공두부(MajorCategory.두부),

    // 건조식품
    건조과일(MajorCategory.건조식품),
    건미역(MajorCategory.건조식품),
    통조림(MajorCategory.건조식품),

    // 기타
    과자(MajorCategory.기타),
    과채음료(MajorCategory.기타),
    탄산음료(MajorCategory.기타);

    private final MajorCategory majorCategory;

    MinorCategory(MajorCategory majorCategory) {
        this.majorCategory = majorCategory;
    }
}

