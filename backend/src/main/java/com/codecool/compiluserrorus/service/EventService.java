package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.model.Event;
import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.repository.EventRepository;
import com.codecool.compiluserrorus.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final MemberService memberService;

    public List<Event> getOrderedEvents() {
        List<Event> events = eventRepository.getAllEvents();
        events.forEach(event -> event.setRomanDate(Util.setRomanDate(event.getEventDate())));
        return events;
    }

    public List<Event> getLatestEvents() {
        List<Event> latestEvents = eventRepository.findTop3ByOrderByEventDateAsc();
        latestEvents.forEach(event -> event.setRomanDate(Util.setRomanDate(event.getEventDate())));
        return latestEvents;
    }

    public void addEvent(Event event, Member member) {
        Member eventCreator = memberService.getLoggedInMember(member);
        event.setCreator(eventCreator);
        eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event event) {
        Event amendEvent = eventRepository.findById(id).orElse(null);
        if (amendEvent != null) {
            amendEvent.setDescription(event.getDescription());
            amendEvent.setEventDate(event.getEventDate());
            amendEvent.setEventTitle(event.getEventTitle());
            eventRepository.save(amendEvent);
        }
        return event;
    }

    public void deleteEvent(Long id) {
        eventRepository.findById(id).ifPresent(deletableEvent -> eventRepository.deleteById(id));
    }
}
