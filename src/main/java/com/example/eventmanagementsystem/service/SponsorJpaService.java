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

import com.example.eventmanagementsystem.repository.SponsorRepository;
import com.example.eventmanagementsystem.repository.EventJpaRepository;
import com.example.eventmanagementsystem.repository.SponsorJpaRepository;
import com.example.eventmanagementsystem.model.*;

@Service

public class SponsorJpaService implements SponsorRepository {

    @Autowired
    private SponsorJpaRepository sponsorJpaRepository;

    @Autowired
    private EventJpaRepository eventJpaRepository;

    @Override
    public List<Sponsor> getSponsors() {
        return new ArrayList<>(sponsorJpaRepository.findAll());
    }

    @Override

    public Sponsor getSponsorById(int sponsorId) {
        Sponsor sponsor = sponsorJpaRepository.findById(sponsorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return sponsor;

    }

    @Override
    public Sponsor addSponsor(Sponsor sponsor) {
        List<Integer> eventIds = new ArrayList<>();

        for (Event event : sponsor.getEvents()) {
            eventIds.add(event.getEventId());
        }
        List<Event> eventsToAdd = eventJpaRepository.findAllById(eventIds);
        if (eventIds.size() != eventsToAdd.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        sponsor.setEvents(eventsToAdd);
        Sponsor SavedSponsor = sponsorJpaRepository.save(sponsor);

        for (Event event : eventsToAdd) {
            event.getSponsors().add(SavedSponsor);
        }

        eventJpaRepository.saveAll(eventsToAdd);
        return SavedSponsor;
    }

    @Override

    public Sponsor updateSponsor(int sponsorId, Sponsor sponsor) {
        Sponsor existingSponsor = sponsorJpaRepository.findById(sponsorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (sponsor.getSponsorName() != null) {
            existingSponsor.setSponsorName(sponsor.getSponsorName());
        }

        if (sponsor.getIndustry() != null) {
            existingSponsor.setIndustry(sponsor.getIndustry());
        }

        if (sponsor.getEvents() != null) {
            List<Integer> eventIds = new ArrayList<>();

            for (Event event : sponsor.getEvents()) {
                eventIds.add(event.getEventId());
            }

            List<Event> events = eventJpaRepository.findAllById(eventIds);
            if (events.size() != eventIds.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            existingSponsor.setEvents(events);

            sponsorJpaRepository.save(existingSponsor);

        }
        return existingSponsor;
    }

    @Override
    public void deleteSponsor(int sponsorId) {
        Sponsor sponsor = sponsorJpaRepository.findById(sponsorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        sponsor.getEvents().clear();

        sponsorJpaRepository.save(sponsor);

        sponsorJpaRepository.delete(sponsor);

        throw new ResponseStatusException(HttpStatus.NO_CONTENT);

    }

    @Override
    public List<Event> getEventsBySponsorId(int sponsorId) {
        Sponsor sponsor = sponsorJpaRepository.findById(sponsorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return sponsor.getEvents();
    }

}