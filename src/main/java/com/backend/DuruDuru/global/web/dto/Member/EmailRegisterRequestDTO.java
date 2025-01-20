package com.backend.DuruDuru.global.web.dto.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRegisterRequestDTO {

    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(max = 200, message = "닉네임은 200자 이내로 입력해주세요.")
    private String nickname;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상 입력해야 합니다.")
    private String password;
}
