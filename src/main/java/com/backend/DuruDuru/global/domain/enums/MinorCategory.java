package com.backend.DuruDuru.global.domain.enums;

import lombok.Getter;

@Getter
public enum MinorCategory {
    // 과일
    사과(MajorCategory.과일, StorageType.냉장, 90,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/237e4f25-f1c1-492b-a799-2862a57d1572"),
    배(MajorCategory.과일, StorageType.냉장, 21,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/6fd43646-6a8b-4fcf-ae7e-206e8faf21df"),
    포도(MajorCategory.과일, StorageType.냉장, 14,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/d95d9930-c09b-4521-8505-62169f6bed64"),
    감(MajorCategory.과일, StorageType.냉장, 14,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/b4f3b7ad-3801-4056-ad2e-68392f66957f"),
    망고(MajorCategory.과일, StorageType.냉장, 3,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/dfa97d30-d01c-493b-9c62-10debd8aab7e"),
    복숭아(MajorCategory.과일, StorageType.실온, 2,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/a6028e29-f679-4ac0-aede-1c5a560c1eb4"),
    멜론(MajorCategory.과일, StorageType.냉장, 6,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/762f873e-916d-4a4e-857e-9f5baf353dd5"),
    파인애플(MajorCategory.과일, StorageType.냉장, 5,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/2aa23c7e-9f82-409e-bb9f-0c94f3a37999"),
    과채류(MajorCategory.과일, StorageType.냉장, 5,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/48df8efd-fe85-4877-b231-2360b21a3783"),
    견과류(MajorCategory.과일, StorageType.실온, 365,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/d72e76f2-b18c-4bd9-ac34-a3d7e1d85cbd"),
    베리류(MajorCategory.과일, StorageType.냉장, 5,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/e61ee5a2-da6e-4630-a34c-c2f5bdffeb1b"),
    감귤류(MajorCategory.과일, StorageType.실온, 21,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/90bcbf01-9750-473d-8d6d-7ac58cdfb2b9"),


    // 육류
    소고기(MajorCategory.육류, StorageType.냉장, 2,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/4459bb05-4236-4161-9c10-3500487b5e53"),
    돼지고기(MajorCategory.육류, StorageType.냉장, 2,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/e108c296-780b-4dbd-b7eb-e4046da484a0"),
    닭고기(MajorCategory.육류, StorageType.냉장, 2,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/b8261f09-a094-4a5a-aa49-a78d2fe384d2"),
    소고기다짐육(MajorCategory.육류, StorageType.냉장, 2,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/913eb04f-a735-40e4-87f6-41d05c5fa9f0"),
    가공육(MajorCategory.육류, StorageType.냉장, 5,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/ed9411b7-2faa-41ce-a24b-74e3e1c177ce"),

    // 수산물
    담백한생선(MajorCategory.수산물, StorageType.냉장, 2,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/91c828b8-95d0-4515-9f54-f05d401b9de6"),
    기름진생선(MajorCategory.수산물, StorageType.냉장, 2,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/f86717af-74d7-449a-b6ed-012d97d6d288"),
    조개류(MajorCategory.수산물, StorageType.냉장, 2,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/5d436d08-c4f6-4448-acde-63b66f32243f"),
    두족류(MajorCategory.수산물, StorageType.냉장, 2,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/29c2cef0-75af-4be8-9462-e87c40f2b14b"),
    갑각류(MajorCategory.수산물, StorageType.냉장, 2,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/6e45f6d6-3e18-419a-911e-c3927d70e13a"),

    어묵(MajorCategory.수산물, StorageType.냉장, 42,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/3e952134-f9a0-4a95-9644-e4fb2f2905cb"),

    // 달걀
    달걀(MajorCategory.달걀, StorageType.냉장, 35,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/1f83c850-5663-405e-ac87-751538e04855"),

    // 유제품
    우유(MajorCategory.유제품, StorageType.냉장, 12,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/ee4b25c3-8ee5-4239-ae34-bf520a801ffd"),
    버터(MajorCategory.유제품, StorageType.냉장, 90,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/cff1ee14-4f24-485e-abec-a1756489f3cd"),
    요거트(MajorCategory.유제품, StorageType.냉장, 20,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/92ab58e7-6bc4-483e-b36d-1c0de6b15b3e"),
    치즈(MajorCategory.유제품, StorageType.냉장, 120,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/a37192cb-a79a-4620-b4d4-42923dc46e07"),
    아이스크림(MajorCategory.유제품, StorageType.냉동, 365,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/624337d5-b686-4cf9-b8a6-0373433cabdb"),
    크림(MajorCategory.유제품, StorageType.냉장, 14,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/04cffa84-200d-4a7f-8c47-adefebff3a04"),

    // 채소
    잎채소(MajorCategory.채소, StorageType.냉장, 3,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/e4a2f9c9-8e35-457a-b3a4-abf51a1108cd"),
    뿌리채소(MajorCategory.채소, StorageType.냉장, 14,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/42d3955a-b2be-4470-9bdb-dff2d1bbb109"),
    //과채류(MajorCategory.채소, StorageType.냉장, 5),
    콩류(MajorCategory.채소, StorageType.실온, 150,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/32a05b60-c114-460b-9c4a-d0aafc6f3f58"),
    마늘(MajorCategory.채소, StorageType.냉장, 20,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/6c89f379-7c36-41e7-9553-200a2c0803fd"),
    대파(MajorCategory.채소, StorageType.냉장, 14,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/80e7669a-3085-4f4e-896c-d4a7f4010b1f"),
    양파(MajorCategory.채소, StorageType.실온, 70,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/b8071580-d212-4cf7-9236-023ac25c7a81"),
    숙주나물(MajorCategory.채소, StorageType.냉장, 4,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/d9c13bba-6d3d-4c97-97f1-ffae4dab4a62"),
    콩나물(MajorCategory.채소, StorageType.냉장, 7,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/922d51f3-3f83-403c-a6f7-0ff34cf0aa8a"),
    브로콜리(MajorCategory.채소, StorageType.냉장, 6,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/826a6799-e8ae-4fa5-9e32-99dc44cb7e8e"),

    // 버섯
    버섯(MajorCategory.버섯, StorageType.냉장, 10,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/39113ab2-77b4-4f2f-847a-a2d49118c361"),

    // 두부
    두부(MajorCategory.두부, StorageType.냉장, 20,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/7d435c7c-71f9-4337-9803-e60bf6f020ca"),
    가공두부(MajorCategory.두부, StorageType.냉장, 90,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/fac57a1f-e8ce-46d7-9c99-61c002d41330"),

    // 건조식품
    건조과일(MajorCategory.건조식품, StorageType.실온, 150,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/128348bc-d2e7-4c99-9e90-e46213f680bb"),
    건미역(MajorCategory.건조식품, StorageType.실온, 365,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/6d0c6061-4eb4-46b7-8bda-910a2c754b23"),
    건조면류(MajorCategory.건조식품, StorageType.실온, 365,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/ac3ee7e8-0d4d-4e87-8b09-49785d1e36a5"),

    // 가루
    곡물가루(MajorCategory.가루, StorageType.실온, 330,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/9b4b1f07-1cfe-4808-8a20-614216087a34"),
    고춧가루(MajorCategory.가루, StorageType.냉장, 240,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/bd8b33e0-1e7c-42b7-99e9-3e7eac873b01"),
    깨가루(MajorCategory.가루, StorageType.냉장, 150,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/aa89f91a-268e-4ea7-bbe4-192856b1c520"),
    전분(MajorCategory.가루, StorageType.실온, 365,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/d51e41d4-c7e5-41ea-9366-69b357600f71"),
    콩가루(MajorCategory.가루, StorageType.실온, 330,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/b3e79381-e9c9-4ceb-a81e-c405a17415a7"),

    // 기타
    통조림(MajorCategory.기타, StorageType.실온, 365,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/ea5f1e2a-48b5-4a51-89d2-a99352890354"),
    과자(MajorCategory.기타, StorageType.실온, 90,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/41295b31-003c-44f4-b83c-d1442d2b16d0"),
    과채음료(MajorCategory.기타, StorageType.냉장, 20,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/27d26b56-f5af-4274-bdb2-0ca99d8eaab9"),
    탄산음료(MajorCategory.기타, StorageType.냉장, 365,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/c7c781f6-7336-4e59-ae96-ba05bfb61c01"),
    묵류(MajorCategory.기타, StorageType.냉장, 16,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/8ecfa00c-d2d6-4008-9669-42d05e284147"),
    장류(MajorCategory.기타, StorageType.냉장, 270,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/e5922207-32bd-46fe-b28e-843029fe2c85"),
    밀떡(MajorCategory.기타, StorageType.냉동, 270,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/1c7477e5-81e6-4d17-b969-1cede750b359"),
    쌀떡(MajorCategory.기타, StorageType.냉동, 270,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/f9b0816c-1605-449f-b6ac-16c93512f7ba"),
    라면(MajorCategory.기타, StorageType.실온, 300,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/dccb275d-8ac4-43b4-b46f-ecd238686142"),
    생면(MajorCategory.기타, StorageType.냉장, 12,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/7c574419-fd8f-4d3f-9919-5ad398314d75"),
    소스류(MajorCategory.기타, StorageType.냉장, 180,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/26d38b29-7911-44d0-9a85-68785d5d6e4a"),


    냉동면류(MajorCategory.기타, StorageType.냉동, 365,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/ae608d04-f199-4f0a-bb2d-71f9e982bf7e"),
    초콜릿(MajorCategory.기타, StorageType.실온, 90,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/41295b31-003c-44f4-b83c-d1442d2b16d0"),
    조미료류(MajorCategory.기타, StorageType.실온, 90,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/951aa83b-e3a4-4b6a-b040-195efa57b41d"),
    기타(MajorCategory.기타, StorageType.실온, 90,"https://duruduru.s3.ap-northeast-2.amazonaws.com/files/ea463c2d-fefb-4df5-a10f-d687d14105f8");




    private final MajorCategory majorCategory;
    private final StorageType storageType;
    private final int shelfLifeDays;
    private final String minorImageUrl;

    MinorCategory(MajorCategory majorCategory, StorageType storageType, int shelfLifeDays, String minorImageUrl) {
        this.majorCategory = majorCategory;
        this.storageType = storageType;
        this.shelfLifeDays = shelfLifeDays;
        this.minorImageUrl = minorImageUrl;

    }

    public static boolean isValidCategory(MajorCategory majorCategory, MinorCategory minorCategory) {
        return minorCategory.getMajorCategory() == majorCategory;
    }

}

