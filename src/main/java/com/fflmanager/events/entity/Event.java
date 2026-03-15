package com.fflmanager.events.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

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
public class Event {

    @GeneratedValue
    @Id
    @Column
    private UUID id;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private String status;

    @Column
    private Date date;

    @Column
    private String venue;

    @Column
    private String location;


}
