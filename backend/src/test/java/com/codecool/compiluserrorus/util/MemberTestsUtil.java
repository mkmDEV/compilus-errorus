package com.codecool.compiluserrorus.util;

import com.codecool.compiluserrorus.model.Member;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class MemberTestsUtil {
    private static PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static Set<Member> getFriendList(int friend) {
        Set<Member> friends = new HashSet<>();

        IntStream.rangeClosed(1, friend).forEach($ -> {

            Member newFriend = Member.builder()
                    .id(2L)
                    .name("name")
                    .email("email@email.com")
                    .password(passwordEncoder.encode("password"))
                    .roles(Set.of("USER"))
                    .build();

            friends.add(newFriend);
        });

        return friends;
    }
}