package com.me.backend.member.service;

import com.me.backend.member.dto.*;
import com.me.backend.product.dto.ProductDTO;

import java.util.List;
import java.util.Map;

public interface MemberService{

    int checkId(Map<String, String> params);

    MemberDTO member(MemberDTO loginMember);

    List<CouponDTO> couponList (String id);

    int signUp(MemberDTO signUpMember);

    MemberDTO loginMember (Map<String, String> params);

    int like (Map<String, String> map);

    int deleteLike (Map<String, String> map);

    int findLike(int id);

    int findInputLike();

    List<ProductDTO> likeList(String id);

    int deleteLikeList(List<ProductDTO> deleteLikeList);

    int findCart(CartDTO product);

    int inputCart(CartDTO product);

    int modifyCartCount(CartDTO product);

    List<CartDTO> findCartList (String id);

    int modifyCart(Map<String, String> map);

    CartDTO findCartCount(Map<String, String> map);

    int deleteCart(List<CartDTO> cartList);
    List<AddressDTO> findAddressList(String id);

    int inputAddress(AddressDTO address);

    int modifyAddressMain (String id);

    int modifyAddress (AddressDTO address);

    int deleteAddress (AddressDTO address);

    int inputOrder (OrderDTO order);

    int inputOrderDetail (List<CartDTO> cartList);

    int deleteCoupon (int id);

    int stockModify (List<CartDTO> cartList);

    int modifyMileage (OrderDTO order);

    List<OrderDTO> orderList (String id);
}
