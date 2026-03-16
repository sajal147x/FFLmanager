package com.fflmanager.events.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Author: Sajal Gupta
 * Date: 3/15/26 4:08 PM
 */
@Entity
@Table(name="fight_participants")
@Getter
@Setter
@Builder
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
