package com.fflmanager.scraper;

import com.fflmanager.LLM.FightCardParser;
import com.fflmanager.LLM.FightCardParserFactory;
import com.fflmanager.dto.Fight;
import com.fflmanager.dto.FightCard;
import com.fflmanager.dto.Fighter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;


import java.io.IOException;

/**
 * Author: Sajal Gupta
 * Date: 3/12/26 9:24 AM
 */
@Service
public class ScraperService {

    public Document getDocFromWebsite(String url){
        Document doc = null;
        try {
             doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .referrer("https://www.google.com")
                    .timeout(10000)
                    .followRedirects(true)
                    .get();
        }
        catch (Exception e){
            System.out.println("ERROR CALLING WEBSITE");
            e.printStackTrace();
        }

        return doc;
    }

    /**
     * method that scrapes the site and returns the fight card in raw text
     * @param url
     * @param websiteType
     * @throws IOException
     */
    public String scrape(Document doc, String websiteType)  {
        return fightCardRaw(doc, websiteType);
    }


    /**
     * method to extract fight card from raw document based on website type
     * @param doc
     * @return
     */
    private String fightCardRaw(Document doc, String websiteType){

        String relevantContent = "";
        Scraper scraper = ScraperFactory.getScraper(websiteType); //get scraper at run time based on param
        relevantContent = scraper.getFightCardRawText(doc);

        return relevantContent;
    }

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) {


        //STEP 1 : get Document from the web
       Document doc = new ScraperService().getDocFromWebsite("https://www.tapology.com/fightcenter/events/136874-ufc-fight-night");

        //STEP 2 : get fight card raw text and images
        Scraper scraper = ScraperFactory.getScraper("TAPOLOGY"); //get scraper at run time based on provider
        String fightCardRawText = scraper.getFightCardRawText(doc);
        String fighterImages = scraper.getFighterImages(doc);

        StringBuilder sb = new StringBuilder();
        sb.append(fightCardRawText);
        sb.append("\n");
        sb.append(fighterImages);

       //STEP 3: parse the fight card raw text and convert to java object using LLM providers
       FightCardParser parser = FightCardParserFactory.getParser("claude"); //get parser at run time based on provider
       FightCard fightCard = parser.parse(sb.toString());

       for (Fight fight : fightCard.getFights()){
           Fighter fighter1 = fight.getFighter1();
           Fighter fighter2 = fight.getFighter2();
           System.out.println(fighter1.getName() + " (" + fighter1.getRecord() + ") " + fighter1.getImageUrl() + " VS " + fighter2.getName() + " (" + fighter2.getRecord() + ")" +  fighter2.getImageUrl());
       }
    }


}
