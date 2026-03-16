package com.fflmanager.events.service;

import com.fflmanager.LLM.ClaudeParser;
import com.fflmanager.LLM.FightCardParser;
import com.fflmanager.LLM.OpenAIParser;

/**
 * Author: Sajal Gupta
 * Date: 3/16/26 3:43 PM
 */
public class CdnStorageFactory {


    /**
     *
     * @param provider
     * @return
     */
    public static CdnStorage getStorageService(String provider) {
        switch (provider.toLowerCase()) {
            case "supabase":
                return new SupabaseStorage();
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }
}
