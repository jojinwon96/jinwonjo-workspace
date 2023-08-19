package com.me.backend.member.mapper;

import com.me.backend.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberMapper {
    MemberDTO member(MemberDTO loginMember);
    int signUp(MemberDTO signUpMember);
    MemberDTO loginMember (String id);
}
