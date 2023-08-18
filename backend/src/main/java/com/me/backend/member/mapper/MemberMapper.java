package com.me.backend.member.mapper;

import java.util.ArrayList;

import com.me.backend.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberMapper {
    ArrayList<MemberVO> getMemberList();

    MemberVO member(MemberVO loginMember);
}
