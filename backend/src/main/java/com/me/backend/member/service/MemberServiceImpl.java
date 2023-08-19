package com.me.backend.member.service;

import com.me.backend.member.mapper.MemberMapper;
import com.me.backend.member.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public MemberDTO member(MemberDTO loginMember) {
        return memberMapper.member(loginMember);
    }

    @Override
    public int signUp(MemberDTO signUpMember) {
        return memberMapper.signUp(signUpMember);
    }

    @Override
    public MemberDTO loginMember(String id) {
        return memberMapper.loginMember(id);
    }


}


