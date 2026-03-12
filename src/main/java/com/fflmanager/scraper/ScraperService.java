package com.fflmanager.scraper;

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

    public void scrape(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Accept-Language", "en-US,en;q=0.5")
                .referrer("https://www.google.com")
                .timeout(10000)
                .followRedirects(true)
                .get();

        System.out.println(doc.toString());
    }


    public static void main(String[] args) throws IOException {
        new ScraperService().scrape("https://www.tapology.com/fightcenter/events/136874-ufc-fight-night");
    }


}
