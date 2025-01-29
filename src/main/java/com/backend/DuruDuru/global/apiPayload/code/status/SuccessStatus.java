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
    // 식재료 관련 응답
    INGREDIENT_OK(HttpStatus.OK, "INGREDIENT_4000", "성공입니다."),
    // 냉장고 관련 응답
    FRIDGE_OK(HttpStatus.OK, "FRIDGE_5000", "성공입니다."),
    // 레시피 관련 응답
    RECIPE_OK(HttpStatus.OK, "RECIPE_6000", "성공입니다."),
    // 채팅 관련 응답
    CHAT_OK(HttpStatus.OK, "CHAT_7000", "성공입니다.");


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