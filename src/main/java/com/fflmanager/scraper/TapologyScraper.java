package com.fflmanager.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Author: Sajal Gupta
 * Date: 3/12/26 10:43 AM
 */
public class TapologyScraper implements Scraper{

    /**
     *
     * @param doc
     * @return
     */
    public String getFightCardRawText(Document doc) {
        Element fightCard = doc.getElementById("sectionFightCard");
        return fightCard.text();
    }
}
