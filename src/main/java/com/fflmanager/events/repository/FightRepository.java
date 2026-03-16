package com.fflmanager.events.repository;

import com.fflmanager.events.entity.Fight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Author: Sajal Gupta
 * Date: 3/15/26 10:21 PM
 */
public interface FightRepository extends JpaRepository<Fight, UUID> {
}
