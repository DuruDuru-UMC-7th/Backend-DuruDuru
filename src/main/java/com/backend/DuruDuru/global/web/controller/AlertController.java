package com.backend.DuruDuru.global.web.controller;


import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@Slf4j
@RequestMapping("/Alert")
@Tag(name = "알림 API", description = "알림 관련 API입니다.")
public class AlertController {
    // FCM 토큰 등록
    @PostMapping("/fcm/token")
    @Operation(summary = "FCM 토큰 등록 API", description = "사용자의 FCM 토큰 서버에 등록 API")
    public ApiResponse<?> registerFcmToken(@RequestBody String fcmToken) {
        return ApiResponse.onSuccess(SuccessStatus.CHAT_OK, null);
    }
}

