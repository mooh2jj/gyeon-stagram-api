package com.dsg.gyeonstagramapi.controller;

import com.dsg.gyeonstagramapi.dto.JoinRequestDTO;
import com.dsg.gyeonstagramapi.dto.LoginDTO;
import com.dsg.gyeonstagramapi.props.JwtProps;
import com.dsg.gyeonstagramapi.service.MemberService;
import com.dsg.gyeonstagramapi.util.CookieUtil;
import com.dsg.gyeonstagramapi.util.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final JWTUtil jwtUtil;
    private final JwtProps jwtProps;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody JoinRequestDTO request) {
        log.info("join: {}", request);
        memberService.join(request);
        return ResponseEntity.ok().build();
    }


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class LoginResponseDTO {
        private String email;
        private String name;
        private List<String> roles;
        private String accessToken;
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO request, HttpServletResponse response) {
        log.info("login: {}", request);
        Map<String, Object> loginClaims = memberService.login(request.getEmail(), request.getPassword());

        // 로그인 성공시 accessToken, refreshToken 생성
        String refreshToken = jwtUtil.generateToken(loginClaims, jwtProps.getRefreshTokenExpirationPeriod());
        String accessToken = loginClaims.get("accessToken").toString();

        // refreshToken 쿠키로 클라이언트에게 전달
        CookieUtil.setTokenCookie(response, "refreshToken", refreshToken, jwtProps.getRefreshTokenExpirationPeriod()); // 1day

        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                .email(loginClaims.get("email").toString())
                .name(loginClaims.get("name").toString())
                .roles((List<String>) loginClaims.get("roleNames"))
                .accessToken(accessToken)
                .build();

        log.info("loginResponseDTO: {}", loginResponseDTO);
        // 로그인 성공시, accessToken, email, name, roles 반환
        return ResponseEntity.ok(loginResponseDTO);
    }


    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        log.info("logout");
        // accessToken은 react 내 redux 상태 지워서 없앰
        // 쿠키 삭제
        CookieUtil.removeTokenCookie(response, "refreshToken");

        return ResponseEntity.ok("logout success!");
    }
}
