package com.me.backend.member.service;

import com.me.backend.member.dto.MemberDTO;

import java.util.HashMap;
import java.util.Map;

public interface MemberService{

    int checkId(Map<String, String> params);

    MemberDTO member(MemberDTO loginMember);

    int signUp(MemberDTO signUpMember);

    MemberDTO loginMember (Map<String, String> params);
}
