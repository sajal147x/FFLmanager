package com.fflmanager.events.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Author: Sajal Gupta
 * Date: 3/15/26 4:01 PM
 */
@Entity
@Table(name="fighters")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fighter {

    @GeneratedValue
    @Id
    private UUID id;

    @Column
    private String name;

    @Column
    private String nickname;

    @Column
    private String nationality;

    @Column
    private String imageUrl;

    @Column
    private int age;

    @Column
    private Double height;

    @Column
    private int weight;

    @Column
    private int reach;

    @Column
    private String record;


}
