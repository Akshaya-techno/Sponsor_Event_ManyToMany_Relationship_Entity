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
import com.example.eventmanagementsystem.service.SponsorJpaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SponsorController {

    @Autowired
    private SponsorJpaService sponsorJpaService;

    @GetMapping("/events/sponsors")
    public List<Sponsor> getSponsors() {
        return sponsorJpaService.getSponsors();
    }

    @PostMapping("/events/sponsors")
    public Sponsor addSponsor(@RequestBody Sponsor sponsor) {
        return sponsorJpaService.addSponsor(sponsor);
    }

    @GetMapping("/events/sponsors/{sponsorId}")
    public Sponsor getSponsorById(@PathVariable int sponsorId) {
        return sponsorJpaService.getSponsorById(sponsorId);
    }

    @PutMapping("/events/sponsors/{sponsorId}")
    public Sponsor updateSponsor(@PathVariable int sponsorId,
            @RequestBody Sponsor sponsor) {
        return sponsorJpaService.updateSponsor(sponsorId, sponsor);
    }

    @DeleteMapping("/events/sponsors/{sponsorId}")
    public void deleteSponsor(@PathVariable int sponsorId) {
        sponsorJpaService.deleteSponsor(sponsorId);
    }

    @GetMapping("/sponsors/{sponsorId}/events")
    public List<Event> getEventsBySponsorId(@PathVariable int sponsorId) {
        return sponsorJpaService.getEventsBySponsorId(sponsorId);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> Overall commit 1
