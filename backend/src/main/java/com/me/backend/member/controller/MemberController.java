package com.me.backend.member.controller;

import com.me.backend.Jwt.JwtService;
import com.me.backend.Jwt.JwtServiceImpl;
import com.me.backend.member.service.MemberServiceImpl;
import com.me.backend.member.dto.MemberDTO;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MemberController {

    @Autowired
    private MemberServiceImpl memberService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("api/account/signUp")
    public ResponseEntity signUp(@RequestBody MemberDTO member) {

        System.out.println(member);

        String encodePwd = passwordEncoder.encode(member.getCust_pwd());

        member.setCust_pwd(encodePwd);

        int result = memberService.signUp(member);

        if (result > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PostMapping("/api/account/login")
    public ResponseEntity login(@RequestBody Map<String, String> params, HttpServletResponse res, HttpSession session) {

        int result = 0;

        MemberDTO member = memberService.loginMember(params.get("id"));

        if (member != null) {
            String inputPwd = params.get("pwd");
            String getPwd = member.getCust_pwd();

            if (passwordEncoder.matches(inputPwd, getPwd)) {
                member.setCust_pwd("");

                JwtService jwtService = new JwtServiceImpl();
                String token = jwtService.getToken("id", member.getCust_id());

                member.setToken(token);
                session.setAttribute("loginMember", member);

            } else {
                result = 1;
            }
        } else {
            result = 2;
        }

        if (result > 0){
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

}
























