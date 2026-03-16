package com.fflmanager.events.service;

import com.fflmanager.dto.FightCard;
import com.fflmanager.enums.FightCategory;
import com.fflmanager.events.entity.Fight;
import com.fflmanager.events.entity.FightParticipant;
import com.fflmanager.events.entity.Fighter;
import com.fflmanager.events.repository.EventRepository;
import com.fflmanager.events.repository.FightParticipantRepository;
import com.fflmanager.events.repository.FightRepository;
import com.fflmanager.events.repository.FighterRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.fflmanager.events.entity.Event;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;


/**
 * Author: Sajal Gupta
 * Date: 3/15/26 3:46 PM
 */
@Service
public class EventCreationService {

    private final EventRepository eventRepository;
    private final FighterRepository fighterRepository;
    private final FightRepository fightRepository;
    private final FightParticipantRepository fightParticipantRepository;
    private final CdnStorageFactory  cdnStorageFactory;

    public EventCreationService(EventRepository eventRepository,  FighterRepository fighterRepository, FightRepository fightRepository,  FightParticipantRepository fightParticipantRepository,  CdnStorageFactory cdnStorageFactory) {
        this.eventRepository = eventRepository;
        this.fighterRepository = fighterRepository;
        this.fightRepository = fightRepository;
        this.fightParticipantRepository = fightParticipantRepository;
        this.cdnStorageFactory = cdnStorageFactory;
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
        eventRepository.save(event);

        //CREATE FIGHTS
        List<Fight> fights = createFights(fightCard.getFights(), event);


    }

    /**
     *
     * @param fights
     * @param event
     * @return
     */
    private List<Fight> createFights(List<com.fflmanager.dto.Fight> fights, Event event) {
        List<Fight> result = new ArrayList<>();
        int boutOrder = 1;
        for(com.fflmanager.dto.Fight fightDto : fights) {
            Fighter fighter1 = getOrCreateFighter(fightDto.getFighter1());
            Fighter fighter2 = getOrCreateFighter(fightDto.getFighter2());

            Fight fight = Fight.builder()
                    .event(event)
                    .weightClass(fightDto.getWeightClass())
                    .category(computeCategory(fightDto.getCategory()))
                    .status("upcoming")
                    .boutOrder(boutOrder)
                    .build();
            fightRepository.save(fight);
            boutOrder++;
            createFightParticipants(fightDto, fight, fighter1, fighter2);


        }

        return result;
    }

    /**
     *
     * @param category
     * @return
     */
    private String computeCategory(String category) {
        if(category.toLowerCase().contains("main")){
            return "main_card";
        }
        return "prelim";
    }

    /**
     *
     * @param fightDto
     * @param fight
     * @param fighter1
     * @param fighter2
     */
    private void createFightParticipants(com.fflmanager.dto.Fight fightDto, Fight fight, Fighter fighter1, Fighter fighter2) {
        FightParticipant fightParticipant1 = FightParticipant.builder()
                .fight(fight)
                .fighter(fighter1)
                .odds(Integer.parseInt(fightDto.getFighter1().getBettingOdds().replace("+", "")))
                .corner("fighter_1")
                .build();

        FightParticipant fightParticipant2 = FightParticipant.builder()
                .fight(fight)
                .fighter(fighter2)
                .odds(Integer.parseInt(fightDto.getFighter2().getBettingOdds().replace("+", "")))
                .corner("fighter_2")
                .build();

        fightParticipantRepository.save(fightParticipant1);
        fightParticipantRepository.save(fightParticipant2);
    }


    /**
     *
     * @param fighterDto
     * @return
     */
    private Fighter getOrCreateFighter(com.fflmanager.dto.Fighter fighterDto) {
        Fighter fighter = fighterRepository.getByName(fighterDto.getName());
        if(fighter!=null){
            return fighter;
        }
        String imageUrl = null;
        if(fighterDto.getImageUrl()!=null){
            CdnStorage storage = cdnStorageFactory.getStorageService("supabase");
            imageUrl = storage.uploadImageToCdnStorageFromUrl(fighterDto.getImageUrl(), fighterDto.getName());
        }
        fighter = Fighter.builder()
                .name(fighterDto.getName())
                .weight((int) Math.round(Double.parseDouble(fighterDto.getWeight())))
                .record(fighterDto.getRecord())
                .height(computeHeight(fighterDto.getHeight()))
                .reach(computeReach(fighterDto.getReach()))
                .age(computeAge(fighterDto.getAge()))
                .imageUrl(imageUrl)
                .build();
        fighterRepository.save(fighter);
        return fighter;
    }

    /**
     *
     * @param age
     * @return
     */
    private int computeAge(String age) {
        return Integer.parseInt(age.replaceAll("[^0-9]", ""));
    }

    /**
     *
     * @param reach
     * @return
     */
    private int computeReach(String reach) {
        Double reachDouble =   Double.parseDouble(reach.replaceAll("[^0-9.]", ""));
        return (int) Math.round(reachDouble);
    }

    /**
     *
     * @param height
     * @return
     */
    private Double computeHeight(String height) {
        return Double.parseDouble(height.replaceAll("[^0-9.]", ""));
    }

    /**
     *
     * @param date
     * @return
     */
    private OffsetDateTime parseDate(String date) {
        LocalDate localDate = LocalDate.parse(date); // parses "2026-01-01"
        return localDate.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime();
    }
}
