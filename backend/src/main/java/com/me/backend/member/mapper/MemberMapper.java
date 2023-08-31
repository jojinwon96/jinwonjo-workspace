package com.me.backend.member.mapper;

import com.me.backend.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface MemberMapper {

    int checkId(Map<String, String> params);
    MemberDTO member(MemberDTO loginMember);
    int signUp(MemberDTO signUpMember);
    MemberDTO loginMember (Map<String, String> params);
}
