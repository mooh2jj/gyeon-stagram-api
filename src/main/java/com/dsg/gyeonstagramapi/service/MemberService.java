package com.dsg.gyeonstagramapi.service;

import com.dsg.gyeonstagramapi.dto.JoinRequestDTO;
import jakarta.validation.Valid;

import java.util.Map;

public interface MemberService {

    void join(@Valid JoinRequestDTO request);

    Map<String, Object> login(String email, String password);
}
