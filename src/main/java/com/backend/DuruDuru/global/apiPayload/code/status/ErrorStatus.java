package com.backend.DuruDuru.global.apiPayload.code.status;

import com.backend.DuruDuru.global.apiPayload.code.BaseErrorCode;
import com.backend.DuruDuru.global.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // Auth 관련
    AUTH_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_001", "토큰이 만료되었습니다."),
    AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_002", "토큰이 유효하지 않습니다."),
    INVALID_LOGIN_REQUEST(HttpStatus.UNAUTHORIZED, "AUTH_003", "올바른 아이디나 패스워드가 아닙니다."),
    NOT_EQUAL_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_004", "리프레시 토큰이 다릅니다."),
    NOT_CONTAIN_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_005", "해당하는 토큰이 저장되어있지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "AUTH_006", "비밀번호가 일치하지 않습니다."),
    AUTH_INVALID_CODE(HttpStatus.UNAUTHORIZED, "AUTH_008", "코드가 유효하지 않습니다."),
    INVALID_KAKAO_REQUEST_INFO(HttpStatus.UNAUTHORIZED, "AUTH_009", "카카오 정보 불러오기에 실패하였습니다."),
    INVALID_NAVER_REQUEST_INFO(HttpStatus.UNAUTHORIZED, "AUTH_010", "네이버 정보 불러오기에 실패하였습니다."),



    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 회원 관련 에러 1000
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER_1001", "사용자가 없습니다."),
    MEMBER_NAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER_1002", "이름입력은 필수 입니다."),
    MEMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "MEMBER_1003", "이미 존재하는 유저입니다."),
    MEMBER_ID_NULL(HttpStatus.BAD_REQUEST, "MEMBER_1004", "사용자 아이디는 필수 입니다."),
    MEMBER_ADMIN_UNAUTHORIZED(HttpStatus.BAD_REQUEST, "MEMBER_1005", "관리자 권한이 없습니다."),
    MEMBER_LOGIN_FAIL(HttpStatus.BAD_REQUEST, "MEMBER_1006", "아이디나 비밀번호가 올바르지 않습니다."),
    MEMBER_WRONG_EMAIL(HttpStatus.BAD_REQUEST, "MEMBER_1007", "이메일 형식이 올바르지 않습니다."),
    MEMBER_WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER_1008", "비밀번호 형식이 올바르지 않습니다."),
    MEMBER_EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "MEMBER_1009", "이미 가입된 이메일입니다."),
    MEMBER_NICKNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "MEMBER_1010", "이미 존재하는 닉네임입니다."),

    // 레시피 관련 에러
    RECIPE_NOT_FOUND(HttpStatus.NOT_FOUND, "RECIPE_2001", "해당 레시피를 찾을 수 없습니다."),
    RECIPE_INVALID_REQUEST(HttpStatus.BAD_REQUEST, "RECIPE_2002", "잘못된 요청입니다."),

    // Town 관련 에러
    TOWN_NOT_FOUND(HttpStatus.NOT_FOUND, "TOWN_3001", "해당 Town을 찾을 수 없습니다."),
    TOWN_ALREADY_EXISTS(HttpStatus.CONFLICT, "TOWN_3002", "이미 Town이 존재합니다."),
    TOWN_NOT_REGISTERED(HttpStatus.BAD_REQUEST, "TOWN_3003", "Town이 등록되지 않았습니다."),

    // 품앗이 관련 에러
    TRADE_NOT_FOUND(HttpStatus.NOT_FOUND, "TRADE_4001", "해당 게시글을 찾을 수 없습니다."),
    TRADE_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "TRADE_4002", "해당 이미지를 찾을 수 없습니다."),
    TRADE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "TRADE_4003", "해당 게시글에 대한 접근 권한이 없습니다."),
    TRADE_MAX_LIMIT_REACHED(HttpStatus.BAD_REQUEST, "TRADE_4004", "진행 중인 거래 개수가 최대치에 도달했습니다."),
    TRADE_IMAGE_LIMIT_REACHED(HttpStatus.BAD_REQUEST, "TRADE_4005", "등록 가능한 이미지가 최대치에 도달했습니다."),
    TRADE_REGISTER_DENIED(HttpStatus.BAD_REQUEST, "TRADE_4006", "요청한 식재료의 개수가 현재 품앗이 등록 가능한 재고보다 많습니다."),
    TRADE_IMAGE_INVALID(HttpStatus.BAD_REQUEST, "TRADE_4007", "해당 게시글에 포함된 이미지가 아닙니다."),
    TRADE_ALREADY_LIKE(HttpStatus.CONFLICT, "TRADE_4008", "이미 찜하기한 게시글입니다."),
    TRADE_LIKE_NOT_EXIST(HttpStatus.BAD_REQUEST, "TRADE_4009", "찜하기한 게시글이 아닙니다."),

    // 식재료 관련 에러
    INGREDIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "INGREDIENT_5001", "해당 식재료를 찾을 수 없습니다."),
    INGREDIENT_ACCESS_DENIED(HttpStatus.NOT_FOUND, "INGREDIENT_5002", "해당 식재료에 대한 접근 권한이 없습니다.")

    ;



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}

