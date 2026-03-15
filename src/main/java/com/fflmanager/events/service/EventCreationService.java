package com.fflmanager.events.service;

import com.fflmanager.dto.FightCard;
import com.fflmanager.events.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.fflmanager.events.entity.Event;


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
        Event event = new Event();
        event.setName(fightCard.getName());
        event.setDate(fightCard.getStartTime());

        //CREATE FIGHTS

        eventRepository.save(event);

    }
}
