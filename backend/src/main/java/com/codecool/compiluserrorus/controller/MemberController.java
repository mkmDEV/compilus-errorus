package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/members")
@CrossOrigin
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public List<Member> getFriends( @RequestBody Member member) {
        return memberService.getFriends(member);
    }

    @PostMapping("/logged-in-member")
    public Member getLoggedInMember(@RequestBody Member member) {
        return memberService.getLoggedInMember(member);
    }

    @PostMapping("/member")
    public Member getMember(@RequestBody Member member) { return  memberService.getMemberById(member.getId()); }

}
