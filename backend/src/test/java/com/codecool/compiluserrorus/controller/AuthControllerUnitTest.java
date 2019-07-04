package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.UserCredentials;
import com.codecool.compiluserrorus.security.JwtTokenServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerUnitTest {

    @MockBean
    Authentication authentication;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtTokenServices jwtTokenServices;

    @Autowired
    AuthController authController;

    // TODO test failed login
    @Test
    public void testLogin() {
        String email = "test@test.hu";
        String password = "password";

        when(authentication.getAuthorities()).thenReturn(Collections.emptyList());
        when(this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)))
                .thenReturn(authentication);
        String token = "token";
        when(jwtTokenServices.createToken(email, Collections.emptyList())).thenReturn(token);

        UserCredentials data = UserCredentials.builder()
                .email("test@test.hu")
                .password("password")
                .build();
        ResponseEntity result = authController.login(data);

        assertEquals("{roles=[], email=test@test.hu, token=token}", result.getBody().toString());
        verify(authentication).getAuthorities();
        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(email, password));
        verify(jwtTokenServices).createToken(email, Collections.emptyList());
    }
}