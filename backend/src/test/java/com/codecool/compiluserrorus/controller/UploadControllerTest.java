package com.codecool.compiluserrorus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class UploadControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void imATeapot() throws Exception {
        String url = "/teapot";

        this.mockMvc
                .perform(get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isIAmATeapot());

    }

    @Test
    @Order(2)
    void testImageUpload() {

        String imageUploadUrl = "/upload";
        String imageGetUrl = "/image-resource/{image}";


    }

}