package com.fflmanager.events.repository;

import com.fflmanager.events.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Author: Sajal Gupta
 * Date: 3/15/26 3:57 PM
 */
public interface EventRepository extends JpaRepository<Event, UUID> {


}
