package com.fflmanager.LLM;

import com.fflmanager.dto.FightCard;

/**
 * Author: Sajal Gupta
 * Date: 3/12/26 10:37 AM
 */
public interface FightCardParser {

    FightCard parse(String fightCardRawText) ;
}
