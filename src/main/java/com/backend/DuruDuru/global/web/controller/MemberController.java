package com.backend.DuruDuru.global.web.controller;


import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import com.backend.DuruDuru.global.service.MemberService.MemberCommandService;
import com.backend.DuruDuru.global.web.dto.Member.AuthRequestDTO;
import com.backend.DuruDuru.global.web.dto.Member.AuthResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@Slf4j
@RequestMapping("/member")
@Tag(name = "멤버 API", description = "멤버 API입니다.")
public class MemberController {

    private final MemberCommandService memberService;

    // 카카오 로그인 API
//    @Operation(summary = "카카오 로그인 API", description = "카카오 로그인 및 회원가입을 진행하는 API 입니다.")
//    @GetMapping("/login/kakao")
//    public ApiResponse<AuthResponseDTO.OAuthResponse> kakaoLogin(@RequestParam("code") String code) {
//        return ApiResponse.onSuccess(SuccessStatus.USER_KAKAO_LOGIN_OK, memberService.kakaoLogin(code));
//    }

    // iOS용 카카오 로그인 API
    @Operation(summary = "iOS용 카카오 로그인 API", description = "iOS 카카오 SDK에서 직접 받은 액세스 토큰을 이용해 로그인하는 API입니다.")
    @PostMapping("/login/kakao")
    public ApiResponse<AuthResponseDTO.OAuthResponse> kakaoLoginWithToken(@RequestBody AuthRequestDTO.KakaoLoginRequest request) {
        return ApiResponse.onSuccess(SuccessStatus.USER_KAKAO_LOGIN_OK, memberService.kakaoLoginWithToken(request.getAccessToken()));
    }

    // 네이버 로그인 API
    @GetMapping("/login/naver")
    @Operation(summary = "네이버 로그인 API", description = "네이버 로그인 및 회원가입을 진행하는 API 입니다.")
    public ApiResponse<AuthResponseDTO.OAuthResponse> naverLogin(@RequestParam("code") String code) {
        return ApiResponse.onSuccess(SuccessStatus.USER_NAVER_LOGIN_OK, memberService.naverLogin(code));
    }

    // 이메일 회원가입 API
    @PostMapping("/register")
    @Operation(summary = "이메일 회원가입 API", description = "이메일 회원가입을 진행하는 API 입니다.")
    public ApiResponse<?> emailSignUp(@RequestBody @Valid AuthRequestDTO.EmailRegisterRequest request){
        memberService.emailRegister(request);
        return ApiResponse.onSuccess(SuccessStatus.USER_REGISTER_OK, null);
    }

    // 이메일 로그인 API
    @PostMapping("/login/email")
    @Operation(summary = "이메일 로그인 API", description = "이메일과 비밀번호로 로그인을 진행하는 API 입니다.")
    public ApiResponse<AuthResponseDTO.EmailLoginResponse> emailLogin(@RequestBody @Valid AuthRequestDTO.EmailLoginRequest request){
        AuthResponseDTO.EmailLoginResponse response = memberService.emailLogin(request);
        return ApiResponse.onSuccess(SuccessStatus.USER_EMAIL_LOGIN_OK, response);
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "JWT Access Token 재발급 API",
            description = "Refresh Token을 검증하고 새로운 Access Token과 Refresh Token을 응답합니다.")
    public ApiResponse<AuthResponseDTO.TokenRefreshResponse> refresh(@RequestBody AuthRequestDTO.RefreshToken request) {
        AuthResponseDTO.TokenRefreshResponse response = memberService.refreshToken(request.getRefreshToken());
        return ApiResponse.onSuccess(SuccessStatus.USER_REFRESH_OK, response);
    }

}
