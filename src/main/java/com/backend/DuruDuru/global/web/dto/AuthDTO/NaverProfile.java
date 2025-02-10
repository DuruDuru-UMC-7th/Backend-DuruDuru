package com.backend.DuruDuru.global.web.dto.AuthDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverProfile {

    @JsonProperty("response")
    private naverAccount naverAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class naverAccount {
        private String id;
        private String email;
        private String nickname;
        private String gender;
        private String name;
        private String mobile;
    }
}
