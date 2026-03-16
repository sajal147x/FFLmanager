package com.fflmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Date: 3/12/26 11:09 AM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fighter {

    private String name;
    private String bettingOdds;
    private String weight;
    private String height;
    private String age;
    private String record;
    private String reach;
    private String imageUrl;

}
