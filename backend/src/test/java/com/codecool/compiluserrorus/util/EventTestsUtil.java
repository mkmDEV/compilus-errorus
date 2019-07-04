package com.codecool.compiluserrorus.util;

import com.codecool.compiluserrorus.model.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class EventTestsUtil {

    public static List<Event> getEvents(int numberOfEvents) {
        List<Event> events = new ArrayList<>();

        IntStream.rangeClosed(1, numberOfEvents).forEach($ -> {

            Event stubEvent = Event.builder()
                    .eventTitle("test title")
                    .description("test description")
                    .eventDate(LocalDateTime.of(2019, 2, 2, 2, 2))
                    .build();


            events.add(stubEvent);
        });

        return events;
    }
}
