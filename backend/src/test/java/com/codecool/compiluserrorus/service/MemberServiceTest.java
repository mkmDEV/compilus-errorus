package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ComponentScan(basePackageClasses = {MemberService.class})
@DataJpaTest
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberServiceTest {

    @MockBean
    private MemberRepository memberRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberService memberService;

    private Member newMember;
    private String rawPassword;

    @BeforeEach
    public void init() {
        this.rawPassword = "password";
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        this.newMember = Member.builder()
                .name("name")
                .email("email@email.com")
                .password(this.passwordEncoder.encode(this.rawPassword))
                .roles(Set.of("USER"))
                .build();
    }


    @Test
    @Order(1)
    public void TestRegistrationWithNewEmail() {
        when(this.memberRepository.save(this.newMember)).thenReturn(this.newMember);

        Member newRegisteredMember = this.memberService.register(this.newMember);

        assertFalse(newRegisteredMember.getName().isEmpty());
        assertFalse(newRegisteredMember.getEmail().isEmpty());
        assertFalse(newRegisteredMember.getRoles().isEmpty());
        assertTrue(newRegisteredMember.getPassword().startsWith("{bcrypt}"));

        this.newMember.setPassword(newRegisteredMember.getPassword());
        verify(this.memberRepository).save(this.newMember);
    }

    @Test
    @Order(2)
    public void TestRegistrationWithAlreadyRegisteredEmail() {

    }

}