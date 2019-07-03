package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.repository.MemberRepository;
import com.codecool.compiluserrorus.util.MemberTestsUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ComponentScan(basePackageClasses = {MemberService.class})
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberServiceUnitTest {

    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    private Member newMember;
    private Member registeredMember;

    @BeforeEach
    public void init() {
        String rawPassword = "password";
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        this.newMember = Member.builder()
                .name("name")
                .email("email@email.com")
                .password(passwordEncoder.encode(rawPassword))
                .roles(Set.of("USER"))
                .build();

        this.registeredMember = Member.builder()
                .id(1L)
                .name("name")
                .email("email@email.com")
                .password(passwordEncoder.encode(rawPassword))
                .roles(Set.of("USER"))
                .build();
    }


    @Test
    @Order(1)
    public void testRegistrationWithNewEmail() {
        when(this.memberRepository.save(this.newMember)).thenReturn(this.registeredMember);

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
    public void testNewMemberIsNotRegistered() {
        when(this.memberRepository.findByEmail(this.newMember.getEmail())).thenReturn(Optional.empty());
        Member newMember = this.memberService.register(this.newMember);
        assertEquals(newMember.getEmail(), this.newMember.getEmail());
        verify(this.memberRepository).findByEmail(this.newMember.getEmail());
    }

    @Test
    @Order(3)
    public void testRegistrationWithAlreadyRegisteredEmail() {
        when(this.memberRepository.findByEmail(this.newMember.getEmail())).thenReturn(null);
        assertThrows(NullPointerException.class, () -> this.memberService.register(this.newMember));
        verify(this.memberRepository).findByEmail(this.newMember.getEmail());
    }

    @Test
    @Order(4)
    public void getValidLoggedInMemberTest() {
        when(this.memberRepository.findByEmail(this.newMember.getEmail())).thenReturn(Optional.ofNullable(this.registeredMember));
        assertNotNull(this.memberService.getLoggedInMember(this.newMember));
        assertEquals(this.newMember.getEmail(), this.registeredMember.getEmail());
        verify(this.memberRepository).findByEmail(this.newMember.getEmail());
    }

    @Test
    @Order(5)
    public void getInvalidLoggedInMemberTest() {
        when(this.memberRepository.findByEmail(this.newMember.getEmail())).thenReturn(Optional.empty());
        assertNull(this.memberService.getLoggedInMember(this.newMember));
        verify(this.memberRepository).findByEmail(this.newMember.getEmail());
    }

    @Test
    @Order(6)
    public void gettingLoggedInMemberFriends() {
        int friends = 5;
        when(this.memberRepository.findAll()).thenReturn(MemberTestsUtil.getFriendList(friends));
        List<Member> friendList = this.memberService.getFriends(this.newMember);
        assertEquals(friendList.size(), friends);
        verify(this.memberRepository).findAll();
    }
}