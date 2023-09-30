package com.me.backend.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.backend.Jwt.JwtService;
import com.me.backend.Jwt.JwtServiceImpl;
import com.me.backend.member.dto.*;
import com.me.backend.member.service.MemberServiceImpl;
import com.me.backend.product.dto.InquiryDTO;
import com.me.backend.product.dto.ProductDTO;
import com.me.backend.product.dto.ReviewDTO;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class MemberController {

    @Autowired
    private MemberServiceImpl memberService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("api/account/idCheck")
    public ResponseEntity idCheck(@RequestBody Map<String, String> params) {

        int result = memberService.checkId(params);

        return new ResponseEntity(result, HttpStatus.OK);
    }


    @PostMapping("api/account/signUp")
    public ResponseEntity signUp(@RequestBody MemberDTO member) {

        String encodePwd = "";

        if (!member.getCust_id().equals("")) {
            encodePwd = passwordEncoder.encode(member.getCust_pwd());
            member.setCust_pwd(encodePwd);
        } else {
            encodePwd = passwordEncoder.encode(member.getSeller_pwd());
            member.setSeller_pwd(encodePwd);
        }


        int result = memberService.signUp(member);

        if (result > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PostMapping("/api/account/login")
    public ResponseEntity login(@RequestBody Map<String, String> params, HttpServletResponse res, HttpSession session) {

        String target = params.get("target");
        String getPwd = "";

        int result = 0;
        List<CouponDTO> couponList = null;

        MemberDTO member = memberService.loginMember(params);

        if (target.equals("user")) {
            getPwd = member.getCust_pwd();
        } else {
            getPwd = member.getSeller_pwd();
        }

        if (member != null) {
            String inputPwd = params.get("pwd");

            if (passwordEncoder.matches(inputPwd, getPwd)) {
                member.setCust_pwd("");
                member.setSeller_pwd("");

                JwtService jwtService = new JwtServiceImpl();
                String token = "";
                if (target.equals("user")) {
                    token = jwtService.getToken("id", member.getCust_id());
                    couponList = memberService.couponList(member.getCust_id());
                } else {
                    token = jwtService.getToken("id", member.getSeller_id());
                }

                member.setToken(token);
                session.setAttribute("loginMember", member);

            } else {
                result = 1;
            }
        } else {
            result = 2;
        }

        if (result > 0) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            member.setCouponList(couponList);
            return new ResponseEntity<>(member, HttpStatus.OK);
        }

    }

    @GetMapping("/api/account/logout")
    public ResponseEntity logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/account/check")
    public ResponseEntity check(HttpSession session) {

        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");

        ResponseEntity responseEntity = null;

        if (loginMember == null) {
            responseEntity = new ResponseEntity<>(null, HttpStatus.OK);
            return responseEntity;
        }

        List<CouponDTO> couponList = memberService.couponList(loginMember.getCust_id());

        if (loginMember != null) {
            Claims claims = jwtService.getClaims(loginMember.getToken());

            if (claims != null) {
                loginMember.setCouponList(couponList);
                responseEntity = new ResponseEntity<>(loginMember, HttpStatus.OK);
            }
        }

        return responseEntity;
    }

    @PostMapping("/api/account/like")
    public ResponseEntity like(@RequestBody Map<String, String> params) {

        int result = 0;

        int like = memberService.like(params);

        if (like > 0) {
            result = memberService.findInputLike();
        }

        System.out.println(result);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/api/account/delete-like")
    public ResponseEntity deleteLike(@RequestBody Map<String, String> params) {

        int result = 0;

        int deleteLike = memberService.deleteLike(params);

        if (deleteLike > 0) {
            int id = Integer.parseInt(params.get("like_id"));
            System.out.println(id);
            result = memberService.findLike(id);

        }

        System.out.println(result);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/api/account/likeList")
    public ResponseEntity likeList(HttpSession session) {

        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");

        List<ProductDTO> likeList = memberService.likeList(member.getCust_id());

        return new ResponseEntity<>(likeList, HttpStatus.OK);
    }


    @PostMapping("/api/account/delete-likeList")
    public ResponseEntity deleteLikeList(@RequestBody String data, HttpSession session) throws JsonProcessingException {

        // JSON Array 맵핑
        ObjectMapper objectMapper = new ObjectMapper();

        ProductDTO[] products = objectMapper.readValue(data, new TypeReference<ProductDTO[]>() {
        });

        List<ProductDTO> deleteLikeList = new ArrayList(Arrays.asList(products));

        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");

        for (ProductDTO like : deleteLikeList) {
            like.setSeller_id(member.getCust_id());
        }

        int result = memberService.deleteLikeList(deleteLikeList);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/api/account/input-cart")
    public ResponseEntity inputCart(@RequestBody String data) throws JsonProcessingException {
        // JSON Array 맵핑
        ObjectMapper objectMapper = new ObjectMapper();

        CartDTO[] cartList = objectMapper.readValue(data, new TypeReference<CartDTO[]>() {
        });

        List<CartDTO> inputCartList = new ArrayList(Arrays.asList(cartList));

        int result1 = 0,
                result2 = 0;

        for (CartDTO cart : inputCartList) {
            result1 = memberService.findCart(cart);

            if (result1 > 0) { // 장바구니 기존 상품 수정
                result2 = memberService.modifyCartCount(cart);
            } else { // 장바구니 새로 삽입
                result2 = memberService.inputCart(cart);
            }
        }

        return new ResponseEntity<>(result2, HttpStatus.OK);
    }

    @GetMapping("/api/account/cart")
    public ResponseEntity findCartList(HttpSession session) {

        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");

        List<CartDTO> cart = memberService.findCartList(member.getCust_id());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/api/modify-cart")
    public ResponseEntity modifyCart(@RequestBody Map<String, String> params) {

        int result1 = 0;
        CartDTO cart = null;
        result1 = memberService.modifyCart(params);

        if (result1 > 0) {
            cart = memberService.findCartCount(params);
        }

        return new ResponseEntity<>(cart.getCount(), HttpStatus.OK);
    }

    @PostMapping("/api/delete-cart")
    public ResponseEntity deleteCart(@RequestBody String data, HttpSession session) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        CartDTO[] cartList = objectMapper.readValue(data, new TypeReference<CartDTO[]>() {
        });

        List<CartDTO> deleteCartList = new ArrayList(Arrays.asList(cartList));

        int result = memberService.deleteCart(deleteCartList);

        List<CartDTO> findCartList = null;

        if (result > 0) {
            MemberDTO member = (MemberDTO) session.getAttribute("loginMember");
            findCartList = memberService.findCartList(member.getCust_id());
        }

        return new ResponseEntity<>(findCartList, HttpStatus.OK);
    }

    @GetMapping("/api/account/address")
    public ResponseEntity findAddress(HttpSession session) {
        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");

        List<AddressDTO> findAddressList = memberService.findAddressList(member.getCust_id());

        return new ResponseEntity<>(findAddressList, HttpStatus.OK);
    }

    @PostMapping("/api/account/input-address")
    public ResponseEntity inputAddress(@RequestBody AddressDTO address, HttpSession session) {
        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");

        address.setCust_id(member.getCust_id());

        int result1 = 0,
                result2 = 0;

        if (address.getAddr_default().equals("Y")) {
            result2 = memberService.modifyAddressMain(member.getCust_id());
        }

        result1 = memberService.inputAddress(address);

        return new ResponseEntity<>(result1, HttpStatus.OK);
    }

    @PostMapping("/api/account/modify-address")
    public ResponseEntity modifyAddress(@RequestBody AddressDTO address, HttpSession session) {
        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");

        address.setCust_id(member.getCust_id());

        int result1 = 0,
                result2 = 0;

        System.out.println(address);

        if (address.getAddr_default().equals("Y")) {
            result2 = memberService.modifyAddressMain(member.getCust_id());
        }

        result1 = memberService.modifyAddress(address);

        return new ResponseEntity<>(result1, HttpStatus.OK);
    }

    @PostMapping("/api/account/delete-address")
    public ResponseEntity deleteAddress(@RequestBody AddressDTO address) {

        int result = memberService.deleteAddress(address);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/api/account/input-order")
    public ResponseEntity inputOrder(@RequestBody OrderDTO order) {

        int orderSuccess = 0,
                orderDetailSuccess = 0;

        orderSuccess = memberService.inputOrder(order);

        if (orderSuccess > 0) {
            orderDetailSuccess = memberService.inputOrderDetail(order.getOrderList());
        }

        if (orderDetailSuccess > 0) {
            if (order.getCoupon().getCoupon_id() > 0) {
                memberService.deleteCoupon(order.getCoupon().getCoupon_list_id());
            }

            if (order.getMileage() > 0) {
                memberService.modifyMileage(order);
            }

        }

        return new ResponseEntity<>(orderDetailSuccess, HttpStatus.OK);
    }

    @GetMapping("/api/account/order")
    public ResponseEntity order(HttpSession session) {
        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");

        List<OrderDTO> orderList = memberService.orderList(member.getCust_id());
        List<RefundDTO> refundList = memberService.refundList(member.getCust_id());

        if (!refundList.isEmpty()) {
            for (OrderDTO order : orderList) {
                for (RefundDTO refund : refundList) {
                    if (order.getOrder_detail_id() == refund.getOrder_detail_id()) {
                        order.setRefundStatus(refund.getStatus());
                    }
                }
            }
        }

        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @PostMapping("/api/account/input-refund")
    public ResponseEntity inputRefund(@RequestBody RefundDTO refund) {

        int result = memberService.inputRefund(refund);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/api/account/refund")
    public ResponseEntity refund(HttpSession session){

        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");

        List<RefundDTO> refundList = memberService.refundList(member.getCust_id());

        return new ResponseEntity<>(refundList, HttpStatus.OK);
    }

    @GetMapping("/api/account/review")
    public ResponseEntity review(HttpSession session){

        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");

        List<ReviewDTO> reviewList = memberService.reviewList(member.getCust_id());

        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

    @GetMapping("/api/account/inquiry")
    public ResponseEntity inquiry(HttpSession session){

        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");

        List<InquiryDTO> inquiryList = memberService.inquiryList(member.getCust_id());

        return new ResponseEntity<>(inquiryList, HttpStatus.OK);
    }

    @GetMapping("/api/account/myPage-info")
    public ResponseEntity myPageInfo(HttpSession session){

        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");

        int cartCount = memberService.cartCount(member.getCust_id());

        int likeCount = memberService.likeCount(member.getCust_id());

        Map<String, Object> map = new HashMap<>();
        map.put("cartCount", cartCount);
        map.put("likeCount", likeCount);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
























