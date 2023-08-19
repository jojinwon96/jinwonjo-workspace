package com.me.backend.member.service;

import com.me.backend.member.dto.MemberDTO;

public interface MemberService{

    MemberDTO member(MemberDTO loginMember);

    int signUp(MemberDTO signUpMember);

    MemberDTO loginMember (String id);
}
