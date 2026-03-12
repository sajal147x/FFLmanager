package com.fflmanager.scraper;

import com.fflmanager.LLM.FightCardParser;
import com.fflmanager.LLM.FightCardParserFactory;
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

    /**
     * method that scrapes the site and returns the fight card in raw text
     * @param url
     * @param websiteType
     * @throws IOException
     */
    public String scrape(String url, String websiteType) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Accept-Language", "en-US,en;q=0.5")
                .referrer("https://www.google.com")
                .timeout(10000)
                .followRedirects(true)
                .get();

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
    public static void main(String[] args) throws IOException {

        //STEP 1 : get fight card raw text by scraping
       String fightCard = new ScraperService().scrape("https://www.tapology.com/fightcenter/events/136874-ufc-fight-night", "TAPOLOGY");

       //STEP 2 : parse the fight card raw text and convert to java object using LLM providers
       FightCardParser parser = FightCardParserFactory.getParser("claude"); //get parser at run time based on provider
       parser.parse(fightCard);

       System.out.println(fightCard);
    }


}
