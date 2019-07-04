package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.UserCredentials;
import com.codecool.compiluserrorus.security.JwtTokenServices;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerUnitTest {

    private static final String EMAIL = "test@test.hu";
    private static final String PASSWORD = "PASSWORD";

    @MockBean
    private Authentication authentication;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenServices jwtTokenServices;

    @Autowired
    private AuthController authController;

    @Test
    @Order(1)
    public void testSuccessFulLogin() {
        when(this.authentication.getAuthorities()).thenReturn(Collections.emptyList());
        when(this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD)))
                .thenReturn(this.authentication);
        String token = "token";
        when(this.jwtTokenServices.createToken(EMAIL, Collections.emptyList())).thenReturn(token);

        UserCredentials data = UserCredentials.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        ResponseEntity result = this.authController.login(data);

        assertEquals("{roles=[], email=test@test.hu, token=token}", result.getBody().toString());

        verify(this.authentication).getAuthorities();
        verify(this.authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD));
        verify(this.jwtTokenServices).createToken(EMAIL, Collections.emptyList());
    }

    @Test
    @Order(2)
    public void testLoginWithInvalidCredentials() {
        when(this.authentication.getAuthorities()).thenThrow(BadCredentialsException.class);
        when(this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD)))
                .thenReturn(this.authentication);

        UserCredentials data = UserCredentials.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        assertThrows(BadCredentialsException.class, () -> this.authController.login(data));

        verify(this.authentication).getAuthorities();
        verify(this.authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD));
    }
}