package com.me.backend.member.service;

import com.me.backend.member.vo.MemberVO;

import java.util.List;

public interface MemberService{
    List<MemberVO> getMemberList();

    MemberVO member(MemberVO loginMember);


}
