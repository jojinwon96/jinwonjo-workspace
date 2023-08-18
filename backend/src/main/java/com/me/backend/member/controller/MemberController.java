package com.me.backend.member.controller;

import com.me.backend.Jwt.JwtService;
import com.me.backend.Jwt.JwtServiceImpl;
import com.me.backend.member.service.MemberServiceImpl;
import com.me.backend.member.vo.MemberVO;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class MemberController {

    @Autowired
    private MemberServiceImpl memberService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/api/account/login")
    public ResponseEntity login(@RequestBody Map<String, String> params, HttpServletResponse res, HttpSession session) {

        MemberVO loginMember = new MemberVO();
        loginMember.setCust_id(params.get("id"));
        loginMember.setCust_pwd(params.get("pwd"));

        MemberVO member = memberService.member(loginMember);

        if (member != null) {
            JwtService jwtService = new JwtServiceImpl();
            String token = jwtService.getToken("id", member.getCust_id());
            System.out.println(token);

            member.setToken(token);
            session.setAttribute("loginMember", member);

            return new ResponseEntity<>(member, HttpStatus.OK);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/account/logout")
    public ResponseEntity logout(HttpServletRequest request){

        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/account/check")
    public ResponseEntity check(HttpSession session) {

        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");

        if (loginMember != null) {
            Claims claims = jwtService.getClaims(loginMember.getToken());

            if (claims != null) {
                return new ResponseEntity<>(loginMember, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
























