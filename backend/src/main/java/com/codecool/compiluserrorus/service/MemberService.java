package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public Member getMemberById(Long id) {
        Member member = this.memberRepository.findById(id).orElse(null);
        return member;
    }

    public Set<Member> getFriends(Member member) {
        Member searchedMember = getLoggedInMember(member);
        if (searchedMember != null) {
            return searchedMember.getFriends();
        }
        return null;
    }

    public Member addFriend(Long id, Member friend) {
        Member member = getMemberById(id);
        Member friendToAdd = getMemberById(friend.getId());

        if (member != null && friendToAdd != null) {
            Set<Member> friends = member.getFriends();
            friends.add(friendToAdd);
            member.setFriends(friends);

            friends = friendToAdd.getFriends();
            friends.add(member);
            friendToAdd.setFriends(friends);

            this.memberRepository.save(member);
            this.memberRepository.save(friendToAdd);
            return member;
        }
        return null;
    }
}
