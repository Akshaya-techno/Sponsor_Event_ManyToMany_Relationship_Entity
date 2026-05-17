/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.web.bind.annotation.*;
 * 
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.eventmanagementsystem.controller;

import com.example.eventmanagementsystem.model.Event;
import com.example.eventmanagementsystem.model.Sponsor;
import com.example.eventmanagementsystem.service.EventJpaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    @Autowired
    private EventJpaService eventJpaService;

    @GetMapping("/events")
    public List<Event> getEvents() {
        return eventJpaService.getEvents();
    }

    @PostMapping("/events")
    public Event addEvent(@RequestBody Event event) {
        return eventJpaService.addEvent(event);
    }

    @GetMapping("/events/{eventId}")
    public Event getEventById(@PathVariable int eventId) {
        return eventJpaService.getEventById(eventId);
    }

    @PutMapping("/events/{eventId}")
    public Event updateEvent(@PathVariable int eventId,
            @RequestBody Event event) {
        return eventJpaService.updateEvent(eventId, event);
    }

    @DeleteMapping("/events/{eventId}")
    public void deleteEvent(@PathVariable int eventId) {
        eventJpaService.deleteEvent(eventId);
    }

    @GetMapping("/events/{eventId}/sponsors")
    public List<Sponsor> getSponsorsByEventId(@PathVariable int eventId) {
        return eventJpaService.getSponsorsByEventId(eventId);
    }
}
