package com.fflmanager.events.entity;

import com.fflmanager.enums.FightCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Author: Sajal Gupta
 * Date: 3/15/26 4:04 PM
 */
@Entity
@Table(name="fights")
@Getter
@Setter
@Builder
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

    @Column(name = "category")
    private String category = "main_card";


    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Fighter winner;


    @Column
    private int round;

    @Column
    private String time;

}
