package com.fflmanager.events.entity;

import com.fflmanager.enums.EventStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

import java.util.UUID;

/**
 * Author: Sajal Gupta
 * Date: 3/15/26 3:38 PM
 */
@Entity
@Table(name="events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @GeneratedValue
    @Id
    @Column
    private UUID id;

    @Column
    private String name;

    @Column
    private String type = "UFC";

    @Column
    private OffsetDateTime date;

    @Column
    private String venue;

    @Column
    private String location;


}
