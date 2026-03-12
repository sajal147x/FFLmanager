package com.fflmanager.scraper;

/**
 * Author: Sajal Gupta
 * Date: 3/12/26 10:45 AM
 */
public class ScraperFactory {

    /**
     *
     * @param website
     * @return
     */
    public static Scraper getScraper(String website) {
        switch (website.toLowerCase()) {
        case "tapology":
          return new TapologyScraper();
        default:
             throw new IllegalArgumentException("Unsupported provider: " + website);
        }
    }
}
