package com.fflmanager.LLM;

/**
 * Author: Sajal Gupta
 * Date: 3/12/26 10:39 AM
 */
public class FightCardParserFactory {


    /**
     *
     * @param provider
     * @return
     */
    public static FightCardParser getParser(String provider) {
        switch (provider.toLowerCase()) {
            case "claude":
                return new ClaudeParser();
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }
}
