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
@RequestMapping("/example")
@Tag(name = "예시 API", description = "예시 API입니다.")
public class ExampleController {

    @PostMapping("/")
    @Operation(summary = "Example-1 API", description = "Example-1 API 입니다.")
    public ApiResponse<?> example1(){
        return ApiResponse.onSuccess(SuccessStatus.EXAMPLE_OK, null);
    }

    @GetMapping("/{example-id}")
    @Operation(summary = "Example-2 API", description = "Example-2 API 입니다.")
    public ApiResponse<?> example2(){
        return ApiResponse.onSuccess(SuccessStatus.EXAMPLE_OK, null);
    }



}
