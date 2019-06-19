package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Event;
import com.codecool.compiluserrorus.service.EventService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/events")
@CrossOrigin
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getEvents() {
        return eventService.getOrderedEvents();
    }

    @GetMapping("/latest")
    public List<Event> getLatestEvents() {
        return eventService.getLatestEvents();
    }

    @PostMapping
    public Event addEvent(@Valid @RequestBody Event event) {
        eventService.addEvent(event);
        return event;
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable("id") Long id, @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable("id") Long id) {
        eventService.deleteEvent(id);
    }
}
