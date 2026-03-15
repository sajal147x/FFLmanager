package com.fflmanager.dto;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Date: 3/12/26 10:38 AM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FightCard {
	private List<Fight> fights;
	private String date;
	private String name;
}
