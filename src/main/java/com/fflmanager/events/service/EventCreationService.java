package com.fflmanager.events.service;

import com.fflmanager.dto.FightCard;
import com.fflmanager.events.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.fflmanager.events.entity.Event;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


/**
 * Author: Sajal Gupta
 * Date: 3/15/26 3:46 PM
 */
@Service
public class EventCreationService {

    private final EventRepository eventRepository;

    public EventCreationService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * method to make entries in the database from the fight card
     * @param fightCard
     */
    @Transactional
    public void createEventFromParsedFightCard(FightCard fightCard) {

        //CREATE AND SAVE EVENT
        Event event = Event.builder()
                .type(fightCard.getPromotion())
                .name(fightCard.getName())
                .date(parseDate(fightCard.getDate()))
                .location(fightCard.getLocation())
                .venue(fightCard.getVenue())
                .build();

        //CREATE FIGHTS

        eventRepository.save(event);

    }

    private OffsetDateTime parseDate(String date) {
        LocalDate localDate = LocalDate.parse(date); // parses "2026-01-01"
        return localDate.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime();
    }
}
