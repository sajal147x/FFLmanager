package com.fflmanager.events.repository;

import com.fflmanager.events.entity.FightParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Author: Sajal Gupta
 * Date: 3/15/26 10:25 PM
 */
public interface FightParticipantRepository extends JpaRepository<FightParticipant, UUID> {
}
