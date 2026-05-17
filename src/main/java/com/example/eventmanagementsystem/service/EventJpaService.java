/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * 
 * import java.util.*;
 *
 */

// Write your code here
package com.example.eventmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import com.example.eventmanagementsystem.repository.EventRepository;
import com.example.eventmanagementsystem.repository.SponsorJpaRepository;
import com.example.eventmanagementsystem.repository.EventJpaRepository;
import com.example.eventmanagementsystem.model.*;

@Service

public class EventJpaService implements EventRepository {

    @Autowired
    private EventJpaRepository eventJpaRepository;

    @Autowired
    private SponsorJpaRepository sponsorJpaRepository;

    @Override
    public List<Event> getEvents() {
        return new ArrayList<>(eventJpaRepository.findAll());
    }

    @Override
    public Event getEventById(int eventId) {
        Event event = eventJpaRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return event;
    }

    @Override
    public Event addEvent(Event event) {
        // extracts sponsorIds
        List<Integer> sponsorIds = new ArrayList<>();

        // added to sponsorIds list
        for (Sponsor sponsor : event.getSponsors()) {
            sponsorIds.add(sponsor.getSponsorId());
        }

        List<Sponsor> sponsors = sponsorJpaRepository.findAllById(sponsorIds);

        event.setSponsors(sponsors);

        for (Sponsor sponsor : sponsors) {
            sponsor.getEvents().add(event);
        }

        Event savedEvent = eventJpaRepository.save(event);
        return savedEvent;

    }

    @Override
    public Event updateEvent(int eventId, Event event) {
        Event NewEvent = eventJpaRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (event.getEventName() != null) {
            NewEvent.setEventName(event.getEventName());
        }
        if (event.getDate() != null) {
            NewEvent.setDate(event.getDate());
        }
        if (event.getSponsors() != null) {
            // removed old event oldSponsors assossiation
            List<Sponsor> sponsors = NewEvent.getSponsors();
            for (Sponsor sponsor : sponsors) {
                sponsor.getEvents().remove(NewEvent);
            }

            sponsorJpaRepository.saveAll(sponsors);

            List<Integer> newSponsorIds = new ArrayList<>();
            for (Sponsor sponsor : event.getSponsors()) {
                newSponsorIds.add(sponsor.getSponsorId());
            }

            List<Sponsor> newSponsors = sponsorJpaRepository.findAllById(newSponsorIds);

            for (Sponsor sponsor : newSponsors) {
                sponsor.getEvents().add(NewEvent);
            }

            sponsorJpaRepository.saveAll(newSponsors);

            NewEvent.setSponsors(newSponsors);

        }
        return eventJpaRepository.save(NewEvent);
    }

    @Override
    public void deleteEvent(int eventId) {
        Event event = eventJpaRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Sponsor> sponsors = event.getSponsors();
        for (Sponsor sponsor : sponsors) {
            sponsor.getEvents().remove(event);
        }
        sponsorJpaRepository.saveAll(sponsors);

        eventJpaRepository.deleteById(eventId);

        throw new ResponseStatusException(HttpStatus.NO_CONTENT);

    }

    @Override
    public List<Sponsor> getSponsorsByEventId(int eventId) {
        Event event = eventJpaRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return event.getSponsors();
    }
}