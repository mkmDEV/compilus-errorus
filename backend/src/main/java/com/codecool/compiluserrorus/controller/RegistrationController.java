package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class RegistrationController {

    private final MemberService memberService;

    @Autowired
    public RegistrationController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public Member register(@RequestBody Member member) {
        System.out.println("request received");
        return memberService.register(member);
    }
}
