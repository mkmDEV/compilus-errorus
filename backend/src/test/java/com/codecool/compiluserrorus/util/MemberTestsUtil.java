package com.codecool.compiluserrorus.util;

import com.codecool.compiluserrorus.model.Member;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class MemberTestsUtil {
    private static PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static List<Member> getFriendList(int friend) {
        List<Member> friends = new ArrayList<>();

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