package com.fflmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Date: 3/12/26 11:08 AM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fight {

    private Fighter fighter1;
    private Fighter fighter2;
    private String weightClass;
    private String numRounds;

}
