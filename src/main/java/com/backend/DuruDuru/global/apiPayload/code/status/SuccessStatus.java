package com.backend.DuruDuru.global.apiPayload.code.status;

import com.backend.DuruDuru.global.apiPayload.code.BaseCode;
import com.backend.DuruDuru.global.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 보안
    USER_EMAIL_LOGIN_OK(HttpStatus.OK, "AUTH2001", "회원 이메일 로그인이 완료되었습니다."),
    USER_KAKAO_LOGIN_OK(HttpStatus.OK, "AUTH2001", "회원 카카오 로그인이 완료되었습니다."),
    USER_NAVER_LOGIN_OK(HttpStatus.OK, "AUTH2002", "회원 네이버 로그인이 완료되었습니다."),
    USER_DELETE_OK(HttpStatus.OK, "AUTH2002", "회원 탈퇴가 완료되었습니다."),
    USER_REFRESH_OK(HttpStatus.OK, "AUTH2003", "토큰 재발급이 완료되었습니다."),
    USER_REGISTER_OK(HttpStatus.OK, "AUTH2000", "회원 가입이 완료되었습니다."),
    // 예시 응답
    EXAMPLE_OK(HttpStatus.OK, "EXAMPLE_0000", "성공입니다."),
    // 유저 관련 응답
    MEMBER_OK(HttpStatus.OK, "MEMBER_1000", "성공입니다."),
    // OCR 관련 응답
    OCR_OK(HttpStatus.OK, "OCR_2000", "성공입니다."),
    // 품앗이 관련 응답
    TRADE_OK(HttpStatus.OK, "TRADE_3000", "성공입니다."),
    TRADE_CREATE_OK(HttpStatus.OK, "TRADE_3001", "품앗이 게시글을 성공적으로 작성하였습니다."),
    TRADE_GET_DETAIL_OK(HttpStatus.OK, "TRADE_3002", "품앗이 게시글을 성공적으로 상세 조회하였습니다."),
    TRADE_DELETE_OK(HttpStatus.OK, "TRADE_3003", "품앗이 게시글을 성공적으로 삭제하였습니다."),
    TRADE_UPDATE_OK(HttpStatus.OK, "TRADE_3004", "품앗이 게시글을 성공적으로 수정하였습니다."),
    TRADE_GET_LIST_OK(HttpStatus.OK, "TRADE_3005", "품앗이 게시글 리스트를 성공적으로 조회하였습니다."),
    TRADE_LIKE_OK(HttpStatus.OK, "TRADE_3006", "찜하기를 설정하였습니다."),
    TRADE_LIKE_DELETE_OK(HttpStatus.OK, "TRADE_3007", "찜하기를 취소하였습니다"),
    TRADE_LIKE_COUNT_OK(HttpStatus.OK, "TRADE_3008", "찜하기 개수를 성공적으로 조회하였습니다."),
    // 식재료 관련 응답
    INGREDIENT_OK(HttpStatus.OK, "INGREDIENT_4000", "성공입니다."),
    INGREDIENT_CREATE_OK(HttpStatus.OK, "INGREDIENT_4001", "식재료를 성공적으로 추가하였습니다."),
    INGREDIENT_UPDATE_OK(HttpStatus.OK, "INGREDIENT_4002", "식재료를 성공적으로 수정하였습니다."),
    INGREDIENT_DELETE_OK(HttpStatus.OK, "INGREDIENT_4003", "식재료를 성공적으로 삭제하였습니다."),
    INGREDIENT_GET_DETAIL_OK(HttpStatus.OK, "INGREDIENT_4004", "식재료를 성공적으로 상세 조회하였습니다."),
    INGREDIENT_GET_LIST_OK(HttpStatus.OK, "INGREDIENT_4005", "식재료 리스트를 성공적으로 조회하였습니다."),
    INGREDIENT_GET_LIST_BY_CATEGORY_OK(HttpStatus.OK, "INGREDIENT_4006", "카테고리별 식재료 리스트를 성공적으로 조회하였습니다."),
    INGREDIENT_GET_LIST_BY_NAME_OK(HttpStatus.OK, "INGREDIENT_4007", "식재료 이름으로 검색한 리스트를 성공적으로 조회하였습니다."),
    INGREDIENT_IMAGE_UPLOAD_OK(HttpStatus.OK, "INGREDIENT_4008", "식재료 이미지를 성공적으로 업로드하였습니다."),
    INGREDIENT_SET_CATEGORY_OK(HttpStatus.OK, "INGREDIENT_4009", "식재료 카테고리를 성공적으로 설정하였습니다."),
    INGREDIENT_SET_STORAGE_TYPE_OK(HttpStatus.OK, "INGREDIENT_4010", "식재료 보관 방법을 성공적으로 설정하였습니다."),
    INGREDIENT_SET_PURCHASE_DATE_OK(HttpStatus.OK, "INGREDIENT_4011", "식재료 구매 날짜를 성공적으로 설정하였습니다."),
    INGREDIENT_SET_EXPIRY_DATE_OK(HttpStatus.OK, "INGREDIENT_4012", "식재료 유통기한을 성공적으로 설정하였습니다."),
    INGREDIENT_GET_MAJOR_TO_MINOR_CATEGORY_OK(HttpStatus.OK, "INGREDIENT_4013", "대분류에 따른 소분류 카테고리를 성공적으로 조회하였습니다."),
    INGREDIENT_GET_ALL_MINOR_CATEGORIES_OK(HttpStatus.OK, "INGREDIENT_4014", "모든 소분류 카테고리를 성공적으로 조회하였습니다."),
    // 냉장고 관련 응답
    FRIDGE_OK(HttpStatus.OK, "FRIDGE_5000", "성공입니다."),
    FRIDGE_GET_RECENT_INGREDIENTS_OK(HttpStatus.OK, "FRIDGE_5001", "최신 등록된 순으로 전체 식재료 리스트 조회 성공"),
    FRIDGE_GET_NEAR_EXPIRY_INGREDIENTS_OK(HttpStatus.OK, "FRIDGE_5002", "소비기한 임박순 식재료 리스트 조회 성공"),
    FRIDGE_GET_FAR_EXPIRY_INGREDIENTS_OK(HttpStatus.OK, "FRIDGE_5003", "소비기한 여유순 식재료 리스트 조회 성공"),
    FRIDGE_GET_RECENT_INGREDIENTS_BY_MAJOR_OK(HttpStatus.OK, "FRIDGE_5004", "대분류 기준 식재료 최신 등록순 리스트 조회 성공"),
    FRIDGE_GET_NEAR_EXPIRY_INGREDIENTS_BY_MAJOR_OK(HttpStatus.OK, "FRIDGE_5005", "대분류 기준 소비기한 임박순 리스트 조회 성공"),
    FRIDGE_GET_FAR_EXPIRY_INGREDIENTS_BY_MAJOR_OK(HttpStatus.OK, "FRIDGE_5006", "대분류 기준 소비기한 여유순 리스트 조회 성공"),
    FRIDGE_GET_RECENT_BY_NAME_OK(HttpStatus.OK, "FRIDGE_5007", "식재료 이름으로 검색한 최신 등록순 리스트 조회 성공"),
    FRIDGE_GET_NEAR_EXPIRY_BY_NAME_OK(HttpStatus.OK, "FRIDGE_5008", "식재료 이름으로 검색한 소비기한 임박순 리스트 조회 성공"),
    FRIDGE_GET_FAR_EXPIRY_BY_NAME_OK(HttpStatus.OK, "FRIDGE_5009", "식재료 이름으로 검색한 소비기한 여유순 리스트 조회 성공"),
    // 레시피 관련 응답
    RECIPE_FETCH_OK(HttpStatus.OK, "RECIPE_6000", "해당 레시피 조회 성공"),
    RECIPE_FAVORITE_SET_OK(HttpStatus.OK, "RECIPE_6001", "레시피 즐겨찾기 설정 성공"),
    RECIPE_FAVORITE_FETCH_OK(HttpStatus.OK, "RECIPE_6002", "즐겨찾기한 레시피 목록 조회 성공"),
    RECIPE_FAVORITE_SORT_OK(HttpStatus.OK, "RECIPE_6003", "즐겨찾기 수로 정렬된 레시피 목록 조회 성공"),
    RECIPE_RECOMMEND_OK(HttpStatus.OK, "RECIPE_6004", "식재료 기반 레시피 목록 조회 성공"),
    // 채팅 관련 응답
    CHAT_OK(HttpStatus.OK, "CHAT_7000", "성공입니다."),
    // 동네 관련 응답
    TOWN_OK(HttpStatus.OK, "TOWN_8000", "성공입니다."),
    TOWN_CREATE_OK(HttpStatus.OK, "TOWN_8001", "동네를 성공적으로 설정하였습니다."),
    TOWN_GET_DETAIL_OK(HttpStatus.OK, "TOWN_8002", "동네를 성공적으로 상세 조회하였습니다."),
    TOWN_UPDATE_OK(HttpStatus.OK, "TOWN_8003", "동네를 성공적으로 수정하였습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}