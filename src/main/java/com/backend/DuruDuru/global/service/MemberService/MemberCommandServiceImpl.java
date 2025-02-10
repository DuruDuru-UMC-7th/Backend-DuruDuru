package com.backend.DuruDuru.global.service.MemberService;

import com.backend.DuruDuru.global.apiPayload.code.status.ErrorStatus;
import com.backend.DuruDuru.global.apiPayload.exception.handler.MemberException;
import com.backend.DuruDuru.global.converter.AuthConverter;
import com.backend.DuruDuru.global.converter.MemberConverter;
import com.backend.DuruDuru.global.domain.entity.Fridge;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.repository.FridgeRepository;
import com.backend.DuruDuru.global.repository.MemberRepository;
import com.backend.DuruDuru.global.security.provider.JwtTokenProvider;
import com.backend.DuruDuru.global.security.provider.KakaoAuthProvider;
import com.backend.DuruDuru.global.security.provider.NaverAuthProvider;
import com.backend.DuruDuru.global.web.dto.AuthDTO.KakaoProfile;
import com.backend.DuruDuru.global.web.dto.AuthDTO.NaverProfile;
import com.backend.DuruDuru.global.web.dto.AuthDTO.OAuthToken;
import com.backend.DuruDuru.global.web.dto.Member.AuthRequestDTO;
import com.backend.DuruDuru.global.web.dto.Member.AuthResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService{

    private final MemberRepository memberRepository;
    private final FridgeRepository fridgeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoAuthProvider kakaoAuthProvider;
    private final NaverAuthProvider naverAuthProvider;

    @Override
    public Member findMemberById(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    @Override
    @Transactional
    public AuthResponseDTO.OAuthResponse kakaoLogin(String code) {
        OAuthToken oAuthToken;
        try {
            oAuthToken = kakaoAuthProvider.requestToken(code);
        } catch (Exception e) {
            throw new MemberException(ErrorStatus.AUTH_INVALID_CODE);
        }

        KakaoProfile kakaoProfile;
        try {
            kakaoProfile =
                    kakaoAuthProvider.requestKakaoProfile(oAuthToken.getAccess_token());
        } catch (Exception e) {
            throw new MemberException(ErrorStatus.INVALID_KAKAO_REQUEST_INFO);
        }

        // 유저 정보 받기
        Optional<Member> queryMember =
                memberRepository.findByEmail(
                        kakaoProfile.getKakaoAccount().getEmail());

        // 가입자 혹은 비가입자 체크해서 로그인 처리
        if (queryMember.isPresent()) {
            Member member = queryMember.get();
            // 회원탈퇴 여부 검증
            if (member.getIsDelete() == 1) {
                throw new MemberException(ErrorStatus.MEMBER_NOT_FOUND);
            } else {
                String accessToken = jwtTokenProvider.createAccessToken(member.getMemberId());
                String refreshToken = jwtTokenProvider.createRefreshToken(member.getMemberId());
                member.updateToken(accessToken, refreshToken);
                memberRepository.save(member);
                return AuthConverter.toOAuthResponse(accessToken, refreshToken, member);
            }
        } else {
            Member member = memberRepository.save(AuthConverter.toMember(kakaoProfile, makeNickname()));
            String accessToken = jwtTokenProvider.createAccessToken(member.getMemberId());
            String refreshToken = jwtTokenProvider.createRefreshToken(member.getMemberId());
            member.updateToken(accessToken, refreshToken);
            memberRepository.save(member);
            return AuthConverter.toOAuthResponse(accessToken, refreshToken, member);
        }
    }

    @Override
    public AuthResponseDTO.OAuthResponse naverLogin(String code) {

        OAuthToken oAuthToken;
        try {
            oAuthToken = naverAuthProvider.requestToken(code);
        } catch (Exception e) {
            throw new MemberException(ErrorStatus.AUTH_INVALID_CODE);
        }

        NaverProfile naverProfile;
        try {
            naverProfile =
                    naverAuthProvider.requestNaverProfile(oAuthToken.getAccess_token());
        } catch (Exception e) {
            throw new MemberException(ErrorStatus.INVALID_NAVER_REQUEST_INFO);
        }

        Optional<Member> queryMember = memberRepository.findByEmail(naverProfile.getNaverAccount().getEmail());

        if (queryMember.isPresent()) {
            Member member = queryMember.get();
            if (member.getIsDelete() == 1) {
                throw new MemberException(ErrorStatus.MEMBER_NOT_FOUND);
            } else {
                String accessToken = jwtTokenProvider.createAccessToken(member.getMemberId());
                String refreshToken = jwtTokenProvider.createRefreshToken(member.getMemberId());
                member.updateToken(accessToken, refreshToken);
                memberRepository.save(member);
                return AuthConverter.toOAuthResponse(accessToken, refreshToken, member);
            }
        } else {
            Member member = memberRepository.save(AuthConverter.toMember(naverProfile));
            String accessToken = jwtTokenProvider.createAccessToken(member.getMemberId());
            String refreshToken = jwtTokenProvider.createRefreshToken(member.getMemberId());
            member.updateToken(accessToken, refreshToken);
            memberRepository.save(member);
            return AuthConverter.toOAuthResponse(accessToken, refreshToken, member);
        }
    }

    @Override
    @Transactional
    public void emailRegister(AuthRequestDTO.EmailRegisterRequest request) {
        // 이메일 중복 검사
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new MemberException(ErrorStatus.MEMBER_EMAIL_ALREADY_EXISTS);
        }

        // 회원 생성
        Member member = Member.builder()
                .nickName(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        memberRepository.save(member);

        // 회원 가입시 자동 개인 냉장고 생성
        Fridge fridge = Fridge.builder()
                .ingredientCount(0)
                .build();
        fridge.setMember(member);
        member.setFridge(fridge);
        fridgeRepository.save(fridge);


    }


    @Override
    @Transactional
    public AuthResponseDTO.EmailLoginResponse emailLogin(AuthRequestDTO.EmailLoginRequest request) {
        // 이메일로 사용자 조회
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_EMAIL_ALREADY_EXISTS));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new MemberException(ErrorStatus.MEMBER_LOGIN_FAIL);
        }

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(member.getMemberId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getMemberId());

        // DB에 refresh token 저장
        member.setRefreshToken(refreshToken);
        member.setAccessToken(accessToken);
        memberRepository.save(member);


        return MemberConverter.toEmailLoginResponse(accessToken, refreshToken, member);
    }

    @Override
    @Transactional
    public AuthResponseDTO.TokenRefreshResponse refreshToken(String refreshToken) {
        jwtTokenProvider.isTokenValid(refreshToken);
        Long id = jwtTokenProvider.getId(refreshToken);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        String newAccessToken = jwtTokenProvider.createAccessToken(id);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(id);
        member.updateToken(newAccessToken, newRefreshToken);
        memberRepository.save(member);
        return MemberConverter.toTokenRefreshResponse(newAccessToken, newRefreshToken);
    }

    // 랜덤 닉네임 생성
    private String makeNickname(){
        List<String> determiners = List.of(
                "예쁜", "멋진", "귀여운", "배고픈", "철학적인", "현학적인", "슬픈", "파란", "비싼", "밝은", "생각하는", "하얀"
        );

        List<String> animals = List.of(
                "토끼", "비버", "강아지", "부엉이", "여우", "호랑이", "문어", "고양이", "미어캣", "다람쥐", "수달", "곰"
        );

        Random random = new Random();
        String determiner = determiners.get(random.nextInt(determiners.size()));
        String animal = animals.get(random.nextInt(animals.size()));
        return determiner + " " + animal;
    }
}
