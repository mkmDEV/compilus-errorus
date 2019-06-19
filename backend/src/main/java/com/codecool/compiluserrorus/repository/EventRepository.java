package com.codecool.compiluserrorus.repository;

import com.codecool.compiluserrorus.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findTop3ByOrderByEventDateAsc();

    @Query("SELECT e FROM Event e ORDER BY e.eventDate ASC")
    List<Event> getAllEvents();

}
