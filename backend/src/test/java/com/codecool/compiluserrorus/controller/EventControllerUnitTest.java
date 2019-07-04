package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Event;
import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.service.EventService;
import com.codecool.compiluserrorus.service.MemberService;
import com.codecool.compiluserrorus.util.EventTestsUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class EventControllerUnitTest {

    private static final String MAIN_URL = "/events";
    private static final Long STUB_ID = 1L;

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
    private String url;

    private static Stream<Boolean> doesEventExist() {
        return Stream.of(true, false);
    }

    @BeforeEach
    public void init() {
        this.testMember = Member.builder()
                .id(1L)
                .name("name")
                .email("email@email.com")
                .roles(Set.of("USER"))
                .build();

        this.testEvent = Event.builder()
                .eventTitle("test title")
                .description("test description")
                .eventDate(LocalDateTime.of(2019, 2, 2, 2, 2))
                .creator(this.testMember)
                .build();
    }

    @Test
    @Order(1)
    @WithMockUser
    public void getEventsWithLoggedInUser() throws Exception {
        int numberOfEvents = 5;
        this.testEvents = EventTestsUtil.getEvents(numberOfEvents);

        when(this.eventService.getOrderedEvents()).thenReturn(this.testEvents);

        MvcResult mvcResult = this.mockMvc
                .perform(
                        get(MAIN_URL)
                )
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

        MvcResult mvcResult = this.mockMvc
                .perform(
                        get(MAIN_URL)
                )
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
        this.mockMvc.perform(get(MAIN_URL))
                .andExpect(status().isForbidden());

        verifyNoMoreInteractions(this.eventService);
    }

    @Test
    @Order(4)
    @WithMockUser
    public void getLatestEventsWithLoggedInUser() throws Exception {
        int numberOfEvents = 3;
        this.testEvents = EventTestsUtil.getEvents(numberOfEvents);

        when(this.eventService.getLatestEvents()).thenReturn(this.testEvents);

        this.url = MAIN_URL + "/latest";

        MvcResult mvcResult = this.mockMvc
                .perform(
                        get(url)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(actualResponseBody, objectMapper.writeValueAsString(this.testEvents));

        verify(this.eventService).getLatestEvents();
        verifyNoMoreInteractions(this.eventService);
    }

    @Test
    @Order(5)
    @WithMockUser
    public void testWithoutLatestEventsWithLoggedInUser() throws Exception {
        when(this.eventService.getLatestEvents()).thenReturn(null);

        this.url = MAIN_URL + "/latest";

        MvcResult mvcResult = this.mockMvc
                .perform(
                        get(url)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertTrue(actualResponseBody.isEmpty());

        verify(this.eventService).getLatestEvents();
        verifyNoMoreInteractions(this.eventService);
    }

    @Test
    @Order(6)
    public void getLatestEventsWithLoggedOutUser() throws Exception {
        this.mockMvc
                .perform(
                        get(MAIN_URL)
                )
                .andExpect(status().isForbidden());

        verifyNoMoreInteractions(this.eventService);
    }

    @Test
    @Order(7)
    @WithMockUser
    public void testingAddEventWithLoggedInUser() throws Exception {
        when(this.eventService.addEvent(this.testEvent, this.testMember)).thenReturn(this.testEvent);

        String requestBody = this.objectMapper.writeValueAsString(this.testEvent);

        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(MAIN_URL)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Event actualResponseBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Event.class);
        assertEquals(actualResponseBody.getEventTitle(), this.testEvent.getEventTitle());
        assertEquals(actualResponseBody.getDescription(), this.testEvent.getDescription());

        verify(this.eventService).addEvent(this.testEvent, this.testMember);
        verifyNoMoreInteractions(this.eventService);
    }

    @Test
    @Order(8)
    public void testingAddEventWithLoggedOutUser() throws Exception {
        String requestBody = this.objectMapper.writeValueAsString(this.testEvent);

        this.mockMvc
                .perform(
                        post(MAIN_URL)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());

        verifyNoMoreInteractions(this.eventService);
    }

    @Test
    @Order(9)
    @WithMockUser
    public void updateEventWithLoggedInUser() throws Exception {
        Event updatedEvent = Event.builder()
                .eventTitle("updated test title")
                .description("updated test description")
                .eventDate(LocalDateTime.of(2019, 2, 10, 2, 2))
                .creator(this.testMember)
                .build();

        when(this.eventService.updateEvent(STUB_ID, this.testEvent)).thenReturn(updatedEvent);

        this.url = MAIN_URL + "/{id}";
        String requestBody = this.objectMapper.writeValueAsString(this.testEvent);

        MvcResult mvcResult = this.mockMvc
                .perform(
                        put(url, STUB_ID)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Event actualResponseBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Event.class);
        assertEquals(updatedEvent.getEventTitle(), actualResponseBody.getEventTitle());
        assertEquals(updatedEvent.getDescription(), actualResponseBody.getDescription());

        verify(this.eventService).updateEvent(STUB_ID, this.testEvent);
        verifyNoMoreInteractions(this.eventService);
    }


    @Test
    @Order(10)
    public void updateExistingEventWhenLoggedOut() throws Exception {
        this.url = MAIN_URL + "/{id}";
        String requestBody = this.objectMapper.writeValueAsString(this.testEvent);

        this.mockMvc.
                perform(
                        put(this.url, STUB_ID)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());

        verifyNoMoreInteractions(this.eventService);
    }

    @Test
    @Order(11)
    @WithMockUser
    public void updateNonExistingPostWhenLoggedIn() throws Exception {
        when(this.eventService.updateEvent(STUB_ID, this.testEvent)).thenReturn(null);

        this.url = MAIN_URL + "/{id}";
        String requestBody = this.objectMapper.writeValueAsString(this.testEvent);

        MvcResult mvcResult = this.mockMvc.
                perform(
                        put(this.url, STUB_ID)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertTrue(actualResponseBody.isEmpty());

        verify(this.eventService).updateEvent(STUB_ID, this.testEvent);
        verifyNoMoreInteractions(this.eventService);
    }

    @ParameterizedTest
    @Order(12)
    @WithMockUser
    @MethodSource("doesEventExist")
    public void testEventDeleteWithLoggedInUser(boolean doesEventExist) throws Exception {
        when(this.eventService.deleteEvent(STUB_ID)).thenReturn(doesEventExist);

        this.url = MAIN_URL + "/{id}";

        MvcResult mvcResult = this.mockMvc
                .perform(
                        delete(this.url, STUB_ID)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertTrue(actualResponseBody.isEmpty());

        verify(this.eventService).deleteEvent(STUB_ID);
        verifyNoMoreInteractions(this.eventService);
    }

    @Test
    @Order(13)
    public void deletePostWhenLoggedOut() throws Exception {
        this.url = MAIN_URL + "/{id}";

        this.mockMvc
                .perform(delete(this.url, STUB_ID))
                .andExpect(status().isForbidden());

        verifyNoMoreInteractions(this.eventService);
    }
}