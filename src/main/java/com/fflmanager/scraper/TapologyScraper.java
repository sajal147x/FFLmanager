package com.fflmanager.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
        StringBuilder sb = new StringBuilder();
        sb.append(fightCard.text()).append("\n");
        return sb.toString();
    }

    public String getFighterImages(Document doc){
        Element fightCard = doc.getElementById("sectionFightCard");
        Elements images = fightCard.select("img");
        StringBuilder sb = new StringBuilder();
        for(Element img : images) {
            //only fetch headshot images
            if(img.attr("src").contains("headshot_images")){
                sb.append("\n").append(img.attr("src"));
            }
        }
        return sb.toString();
    }
}
