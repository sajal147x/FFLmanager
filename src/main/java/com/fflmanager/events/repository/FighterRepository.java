package com.fflmanager.events.repository;

import com.fflmanager.events.entity.Fighter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Author: Sajal Gupta
 * Date: 3/15/26 10:08 PM
 */
public interface FighterRepository extends JpaRepository<Fighter, UUID> {

    Fighter getByName(String name);
}
