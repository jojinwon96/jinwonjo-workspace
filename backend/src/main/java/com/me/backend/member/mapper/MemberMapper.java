package com.me.backend.member.mapper;

import com.me.backend.member.dto.MemberDTO;
import com.me.backend.member.dto.likeDTO;
import com.me.backend.product.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MemberMapper {

    int checkId(Map<String, String> params);
    MemberDTO member(MemberDTO loginMember);
    int signUp(MemberDTO signUpMember);
    MemberDTO loginMember (Map<String, String> params);

    int like (Map<String, String> map);

    int deleteLike (Map<String, String> map);

    int findLike(int id);

    int findInputLike();

    List<ProductDTO> likeList(String id);

    int deleteLikeList(List<ProductDTO> deleteLikeList);
}
