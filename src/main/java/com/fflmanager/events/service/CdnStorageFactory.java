package com.fflmanager.events.service;

import com.fflmanager.LLM.ClaudeParser;
import com.fflmanager.LLM.FightCardParser;
import com.fflmanager.LLM.OpenAIParser;
import org.springframework.stereotype.Component;

/**
 * Author: Sajal Gupta
 * Date: 3/16/26 3:43 PM
 */
@Component
public class CdnStorageFactory {


    private final SupabaseStorage supabaseStorage;

    public CdnStorageFactory(SupabaseStorage supabaseStorage) {
        this.supabaseStorage = supabaseStorage;
    }
    /**
     *
     * @param provider
     * @return
     */
    public CdnStorage getStorageService(String provider) {
        switch (provider.toLowerCase()) {
            case "supabase":
                return supabaseStorage;
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }
}
