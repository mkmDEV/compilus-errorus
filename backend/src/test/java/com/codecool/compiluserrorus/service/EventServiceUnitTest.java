package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.model.Event;
import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.repository.EventRepository;
import com.codecool.compiluserrorus.util.EventTestsUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ComponentScan(basePackageClasses = {EventService.class})
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventServiceUnitTest {

    private static final Long STUB_ID = 1L;

    @MockBean
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    private List<Event> testEvents;
    private Event testEvent;
    private Member testMember;

    @BeforeEach
    public void init() {
        this.testEvent = Event.builder()
                .eventTitle("test title")
                .description("test description")
                .eventDate(LocalDateTime.of(2019, 2, 2, 2, 2))
                .build();

        this.testMember = Member.builder()
                .name("Test Name")
                .email("test@email.com")
                .password("testpass")
                .build();
    }

    @Test
    @Order(1)
    public void getOrderedEvents() {
        int numberOfEvents = 5;
        this.testEvents = EventTestsUtil.getEvents(numberOfEvents);

        when(this.eventRepository.getAllEvents()).thenReturn(this.testEvents);
        List<Event> orderedEvents = this.eventService.getOrderedEvents();
        assertEquals(this.testEvents.size(), orderedEvents.size());
        verify(this.eventRepository).getAllEvents();
    }

    @Test
    @Order(2)
    public void getLatestEvents() {
        int numberOfEvents = 3;
        this.testEvents = EventTestsUtil.getEvents(numberOfEvents);

        when(this.eventRepository.findTop3ByOrderByEventDateAsc()).thenReturn(this.testEvents);
        List<Event> latestEvents = this.eventService.getLatestEvents();
        assertEquals(numberOfEvents, latestEvents.size());
        verify(this.eventRepository).findTop3ByOrderByEventDateAsc();
    }

    @Test
    @Order(3)
    public void testAddingNewEvent() {
        when(this.eventRepository.save(this.testEvent)).thenReturn(this.testEvent);
        Event newEvent = this.eventService.addEvent(this.testEvent, this.testMember);
        assertEquals(this.testEvent, newEvent);
        verify(this.eventRepository).save(this.testEvent);
    }

    @Test
    @Order(4)
    public void updateExistingEvent() {
        String updatedTitle = "Updated event title";
        String updatedDescription = "Updated event description";

        Event eventToUpdate = Event.builder()
                .eventTitle(updatedTitle)
                .description(updatedDescription)
                .eventDate(LocalDateTime.of(2019, 2, 2, 2, 2))
                .build();

        when(this.eventRepository.findById(STUB_ID)).thenReturn(Optional.ofNullable(this.testEvent));
        Event updatedEvent = this.eventService.updateEvent(STUB_ID, eventToUpdate);

        assertEquals(eventToUpdate.getEventTitle(), updatedEvent.getEventTitle());

        verify(this.eventRepository).findById(STUB_ID);
    }

    @Test
    @Order(5)
    public void updateNonExistingEvent() {
        when(this.eventRepository.findById(STUB_ID)).thenReturn(Optional.empty());
        Event updatedEvent = this.eventService.updateEvent(STUB_ID, this.testEvent);
        assertNull(updatedEvent);
        verify(this.eventRepository).findById(STUB_ID);
    }

    @Test
    @Order(6)
    public void deleteExistingPost() {
        when(this.eventRepository.findById(STUB_ID)).thenReturn(Optional.ofNullable(this.testEvent));
        assertTrue(() -> this.eventService.deleteEvent(STUB_ID));
        verify(this.eventRepository).deleteById(STUB_ID);
    }

    @Test
    @Order(7)
    public void deleteNonExistingPost() {
        when(this.eventRepository.findById(STUB_ID)).thenReturn(Optional.empty());
        assertFalse(() -> this.eventService.deleteEvent(STUB_ID));
        verify(this.eventRepository).findById(STUB_ID);
    }
}