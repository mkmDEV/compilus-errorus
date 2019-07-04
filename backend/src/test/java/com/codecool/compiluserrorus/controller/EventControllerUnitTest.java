package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Event;
import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.service.EventService;
import com.codecool.compiluserrorus.service.MemberService;
import com.codecool.compiluserrorus.util.EventTestsUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class EventControllerUnitTest {

    private static final String MAIN_URL = "/events";

    @MockBean
    private EventService eventService;

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private List<Event> testEvents;
    private Event testEvent;
    private Member testMember;

    @BeforeEach
    public void init() {

    }

    @Test
    @Order(1)
    @WithMockUser
    public void getEventsWithLoggedInUser() throws Exception {
        int numberOfEvents = 5;
        this.testEvents = EventTestsUtil.getEvents(numberOfEvents);

        when(this.eventService.getOrderedEvents()).thenReturn(this.testEvents);

        MvcResult mvcResult = this.mockMvc.perform(get(MAIN_URL))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(actualResponseBody, objectMapper.writeValueAsString(this.testEvents));

        verify(this.eventService).getOrderedEvents();
        verifyNoMoreInteractions(this.eventService);
    }

    @Test
    @Order(2)
    @WithMockUser
    public void testWithoutEventsWithLoggedInUser() throws Exception {
        when(this.eventService.getOrderedEvents()).thenReturn(null);

        MvcResult mvcResult = this.mockMvc.perform(get(MAIN_URL))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertTrue(actualResponseBody.isEmpty());

        verify(this.eventService).getOrderedEvents();
        verifyNoMoreInteractions(this.eventService);
    }

    @Test
    @Order(3)
    public void getEventsWithLoggedOutUser() throws Exception {
        int numberOfEvents = 5;
        this.testEvents = EventTestsUtil.getEvents(numberOfEvents);

        when(this.eventService.getOrderedEvents()).thenReturn(this.testEvents);

        this.mockMvc.perform(get(MAIN_URL))
                .andExpect(status().isForbidden());

        verifyNoMoreInteractions(this.eventService);
    }
}