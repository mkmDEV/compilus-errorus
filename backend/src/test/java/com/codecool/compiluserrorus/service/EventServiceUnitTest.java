package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.model.Event;
import com.codecool.compiluserrorus.repository.EventRepository;
import com.codecool.compiluserrorus.util.EventTestsUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ComponentScan(basePackageClasses = {EventService.class})
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventServiceUnitTest {

    @MockBean
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    private List<Event> testEvents;

    @BeforeEach
    public void init() {

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

    }
}