package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/members")
@CrossOrigin
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public Set<Member> getFriends(@RequestBody Member member) {
        return memberService.getFriends(member);
    }

    @PostMapping("/logged-in-member")
    public Member getLoggedInMember(@RequestBody Member member) {
        return memberService.getLoggedInMember(member);
    }

    @PostMapping("/member")
    public Member getMember(@RequestBody Member member) {
        return memberService.getMemberById(member.getId());
    }

    @PutMapping("/member/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody Member member) {
        return memberService.addFriend(id, member);
    }

}
