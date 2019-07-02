package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.repository.EventRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ComponentScan(basePackageClasses = {EventService.class})
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventServiceUnitTest {

    @MockBean
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @BeforeEach
    public void init() {

    }

    @Test
    @Order(1)
    public void getOrderedEvents() {

    }
}