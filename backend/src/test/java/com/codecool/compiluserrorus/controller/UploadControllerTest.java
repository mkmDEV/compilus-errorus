package com.codecool.compiluserrorus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class UploadControllerTest {

    private static final String UPLOAD_URL = "/upload";
    private static final String RESOURCE_URL = "/image-resource/{image}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @WithMockUser
    public void saveImageAsLoggedInUser() throws Exception {

        String fileName = "filename.jpg";
        File file = new File("route" + fileName);

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName,
                "text/plain", "test data".getBytes());

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart(UPLOAD_URL)
                        .file(mockMultipartFile);

        this.mockMvc
                .perform(builder)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(2)
    public void saveImageWhenLoggedOut() throws Exception {

        String fileName = "filename.jpg";
        File file = new File("route" + fileName);

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName,
                "text/plain", "test data".getBytes());

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart(UPLOAD_URL)
                        .file(mockMultipartFile);

        this.mockMvc
                .perform(builder)
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(3)
    public void getImagesTest() throws Exception {

        String fileName = "filename.jpg";

        mockMvc.perform(get(RESOURCE_URL, fileName))
                .andExpect(status().isOk())
                .andExpect(content().contentType("image/jpeg"));

    }
}