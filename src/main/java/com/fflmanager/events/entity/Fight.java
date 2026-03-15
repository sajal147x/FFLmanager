package com.fflmanager.events.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Author: Sajal Gupta
 * Date: 3/15/26 4:04 PM
 */
@Entity
@Table(name="fights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fight {

    @GeneratedValue
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column
    private int boutOrder;

    @Column
    private String weightClass;

    @Column
    private String category;

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Fighter winner;

    @Column
    private String win_method;

    @Column
    private int round;

    @Column
    private String time;

}
