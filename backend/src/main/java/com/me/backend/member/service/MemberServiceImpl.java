package com.me.backend.member.service;

import com.me.backend.member.dto.likeDTO;
import com.me.backend.member.mapper.MemberMapper;
import com.me.backend.member.dto.MemberDTO;
import com.me.backend.product.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public int checkId(Map<String, String> params) {
        return memberMapper.checkId(params);
    }

    @Override
    public MemberDTO member(MemberDTO loginMember) {
        return memberMapper.member(loginMember);
    }

    @Override
    public int signUp(MemberDTO signUpMember) {
        return memberMapper.signUp(signUpMember);
    }

    @Override
    public MemberDTO loginMember(Map<String, String> params) {
        return memberMapper.loginMember(params);
    }

    @Override
    public int like(Map<String, String> map) {
        return memberMapper.like(map);
    }

    @Override
    public int deleteLike(Map<String, String> map) {
        return memberMapper.deleteLike(map);
    }

    @Override
    public int findLike(int id) {
        return memberMapper.findLike(id);
    }

    @Override
    public int findInputLike() {
        return memberMapper.findInputLike();
    }

    @Override
    public List<ProductDTO> likeList(String id) {
        return memberMapper.likeList(id);
    }

    @Override
    public int deleteLikeList(List<ProductDTO> deleteLikeList) {
        return memberMapper.deleteLikeList(deleteLikeList);
    }


}


