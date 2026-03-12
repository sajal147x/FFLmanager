package com.fflmanager.scraper;

import org.jsoup.nodes.Document;

/**
 * Author: Sajal Gupta
 * Date: 3/12/26 10:42 AM
 */
public interface Scraper {

    public String getFightCardRawText(Document doc);
}
