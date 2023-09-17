package com.me.backend.member.service;

import com.me.backend.member.dto.AddressDTO;
import com.me.backend.member.dto.CartDTO;
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

    @Override
    public int findCart(CartDTO product) {
        return memberMapper.findCart(product);
    }

    @Override
    public int inputCart(CartDTO product) {
        return memberMapper.inputCart(product);
    }

    @Override
    public int modifyCartCount(CartDTO product) {
        return memberMapper.modifyCartCount(product);
    }

    @Override
    public List<CartDTO> findCartList(String id) {
        return memberMapper.findCartList(id);
    }

    @Override
    public int modifyCart(Map<String, String> map) {
        return memberMapper.modifyCart(map);
    }

    @Override
    public CartDTO findCartCount(Map<String, String> map) {
        return memberMapper.findCartCount(map);
    }

    @Override
    public int deleteCart(List<CartDTO> cartList) {
        return memberMapper.deleteCart(cartList);
    }

    @Override
    public List<AddressDTO> findAddressList(String id) {
        return memberMapper.findAddressList(id);
    }

    @Override
    public int inputAddress(AddressDTO address) {
        return memberMapper.inputAddress(address);
    }

    @Override
    public int modifyAddressMain(String id) {
        return memberMapper.modifyAddressMain(id);
    }

    @Override
    public int modifyAddress(AddressDTO address) {
        return memberMapper.modifyAddress(address);
    }

    @Override
    public int deleteAddress(AddressDTO address) {
        return memberMapper.deleteAddress(address);
    }

}


