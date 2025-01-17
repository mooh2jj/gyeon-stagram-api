package com.dsg.gyeonstagramapi.service;

import com.dsg.gyeonstagramapi.dto.JoinRequestDTO;
import com.dsg.gyeonstagramapi.entity.Member;
import com.dsg.gyeonstagramapi.enums.MemberRole;
import com.dsg.gyeonstagramapi.props.JwtProps;
import com.dsg.gyeonstagramapi.repository.MemberRepository;
import com.dsg.gyeonstagramapi.security.CustomUserDetailService;
import com.dsg.gyeonstagramapi.security.MemberDTO;
import com.dsg.gyeonstagramapi.util.CookieUtil;
import com.dsg.gyeonstagramapi.util.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final JWTUtil jwtUtil;
    private final JwtProps jwtProps;

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final CustomUserDetailService userDetailService;

    @Override
    public void join(JoinRequestDTO request) {

        memberRepository.findByEmail(request.getEmail())
                .ifPresent(member -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다!");
                });

        Member member = Member.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        member.addRole(MemberRole.USER); // 회원가입시, USER 권한을 부여

        memberRepository.save(member);

    }


    @Override
    public Map<String, Object> login(String email, String password) {

        MemberDTO memberDTO = (MemberDTO) userDetailService.loadUserByUsername(email);
        log.info("memberService login memberDTO: {}", memberDTO);

        if (!passwordEncoder.matches(password, memberDTO.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        Map<String, Object> claims = memberDTO.getClaims();

        String accessToken = jwtUtil.generateToken(claims, jwtProps.getAccessTokenExpirationPeriod());
        String refreshToken = jwtUtil.generateToken(claims, jwtProps.getRefreshTokenExpirationPeriod());

        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);

        return claims;
    }

}
