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

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ComponentScan(basePackageClasses = {MemberService.class})
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberServiceUnitTest {

    private static final Long STUB_ID = 1L;

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
        assertEquals(this.newMember.getEmail(), newMember.getEmail());
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
        int numberOfFriends = 3;
        Set<Member> friends = MemberTestsUtil.getFriends(numberOfFriends);
        this.registeredMember.setFriends(friends);

        when(this.memberRepository.findByEmail(this.registeredMember.getEmail()))
                .thenReturn(Optional.ofNullable(this.registeredMember));

        Set<Member> friendList = this.memberService.getFriends(this.registeredMember);
        assertEquals(numberOfFriends, friendList.size());

        verify(this.memberRepository).findByEmail(this.registeredMember.getEmail());
    }

    @Test
    @Order(7)
    public void getNoneFriendsOfFakeMember() {
        when(this.memberRepository.findByEmail(this.registeredMember.getEmail())).thenReturn(Optional.empty());
        Set<Member> friendList = this.memberService.getFriends(this.registeredMember);
        assertNull(friendList);
        verify(this.memberRepository).findByEmail(this.registeredMember.getEmail());
    }

    @Test
    @Order(8)
    public void getRealMemberById() {
        when(this.memberRepository.findById(STUB_ID)).thenReturn(Optional.ofNullable(this.newMember));
        assertNotNull(this.memberService.getMemberById(STUB_ID));
        verify(this.memberRepository).findById(STUB_ID);
    }

    @Test
    @Order(9)
    public void getMemberByFakeId() {
        when(this.memberRepository.findById(STUB_ID)).thenReturn(Optional.empty());
        assertNull(this.memberService.getMemberById(STUB_ID));
        verify(this.memberRepository).findById(STUB_ID);
    }

    @Test
    @Order(10)
    public void addFriendWithFakeIds() {
        when(this.memberRepository.findById(STUB_ID)).thenReturn(Optional.empty());
        Member member = this.memberService.addFriend(STUB_ID, this.registeredMember);
        assertNull(member);
        verify(this.memberRepository, times(2)).findById(STUB_ID);
    }

    @Test
    @Order(11)
    public void addFriendWithRealIds() {
        when(this.memberRepository.findById(STUB_ID)).thenReturn(Optional.ofNullable(this.registeredMember));
        this.registeredMember.setFriends(MemberTestsUtil.getFriends(3));

        when(this.memberRepository.save(this.registeredMember)).thenReturn(this.registeredMember);
        Member member = this.memberService.addFriend(STUB_ID, this.registeredMember);

        assertNotNull(member);
        assertEquals(member.getFriends().size(), this.registeredMember.getFriends().size());

        verify(this.memberRepository, times(2)).findById(STUB_ID);
        verify(this.memberRepository, times(2)).save(this.registeredMember);
    }
}