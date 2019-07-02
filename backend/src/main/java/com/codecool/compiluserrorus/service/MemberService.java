package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public Member register(Member member) {
        Member newMember = null;

        Optional<Member> optionalMember = this.memberRepository.findByEmail(member.getEmail());
        if (optionalMember.isEmpty()) {
            newMember = Member.builder()
                    .name(member.getName())
                    .email(member.getEmail())
                    .password(passwordEncoder.encode(member.getPassword()))
                    .roles(Set.of("USER"))
                    .build();

            this.memberRepository.save(newMember);
        }

        return newMember;
    }

    public Member getLoggedInMember(Member member) {
        return this.memberRepository.findByEmail(member.getEmail()).orElse(null);
    }

    public List<Member> getFriends() {
        List<Member> friends = this.memberRepository.findAll();
        Member first = friends.get(0);
        friends.remove(first);
        return friends;
    }
}
