package com.me.backend.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.backend.Jwt.JwtService;
import com.me.backend.Jwt.JwtServiceImpl;
import com.me.backend.member.dto.likeDTO;
import com.me.backend.member.service.MemberServiceImpl;
import com.me.backend.member.dto.MemberDTO;
import com.me.backend.product.dto.ProductDTO;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

        MemberDTO member = memberService.loginMember(params);

        if (target.equals("user")){
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
                if (target.equals("user")){
                    token = jwtService.getToken("id", member.getCust_id());
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

        if (loginMember != null) {
            Claims claims = jwtService.getClaims(loginMember.getToken());

            if (claims != null) {
                return new ResponseEntity<>(loginMember, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/api/account/like")
    public ResponseEntity like(@RequestBody Map<String, String> params){

        int result = 0;

        int like = memberService.like(params);

        if (like > 0){
            result = memberService.findInputLike();
        }

        System.out.println(result);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/api/account/delete-like")
    public ResponseEntity deleteLike(@RequestBody Map<String, String> params){

        int result = 0;

        int deleteLike = memberService.deleteLike(params);

        if (deleteLike > 0){
            int id = Integer.parseInt(params.get("like_id"));
            System.out.println(id);
            result = memberService.findLike(id);

        }

        System.out.println(result);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/api/account/likeList")
    public ResponseEntity likeList(HttpSession session){

        MemberDTO member = (MemberDTO)session.getAttribute("loginMember");

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

        for (ProductDTO like : deleteLikeList){
            like.setSeller_id(member.getCust_id());
        }

        int result = memberService.deleteLikeList(deleteLikeList);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
























