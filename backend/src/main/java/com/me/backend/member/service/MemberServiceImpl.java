package com.me.backend.member.service;

import com.me.backend.member.mapper.MemberMapper;
import com.me.backend.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public ArrayList<MemberVO> getMemberList(){
        return memberMapper.getMemberList();
    }
    @Override
    public MemberVO member(MemberVO loginMember) {
        return memberMapper.member(loginMember);
    }


}


