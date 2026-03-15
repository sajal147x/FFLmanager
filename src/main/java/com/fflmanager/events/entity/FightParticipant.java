package com.fflmanager.events.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Author: Sajal Gupta
 * Date: 3/15/26 4:08 PM
 */
@Entity
@Table(name="fight_participants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FightParticipant {

    @Id
    @GeneratedValue
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "fight_id")
    private Fight fight;

    @ManyToOne
    @JoinColumn(name = "fighter_id")
    private Fighter fighter;

    @Column
    private String corner;

    @Column
    private int odds;


}
