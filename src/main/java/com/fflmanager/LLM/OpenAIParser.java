package com.fflmanager.LLM;

import java.io.IOException;
import java.util.Properties;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fflmanager.dto.FightCard;
import org.springframework.http.*;

/**
 * Author: Sajal Gupta
 * Date: 3/15/26 5:27 PM
 */
@Service
public class OpenAIParser implements FightCardParser {

    private String API_KEY;

    public OpenAIParser() {
        try {
            Properties props = new Properties();
            props.load(getClass().getResourceAsStream("/application.properties"));
            API_KEY = props.getProperty("openai.api.key");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FightCard parse(String rawText) {
        try {
            String prompt = """
                    Extract all fights from the following UFC fight card text.
                    Return ONLY a JSON object matching this exact format, no explanation:
                    %s

                    Fight card text:
                    %s
                    """.formatted(loadFormat(), rawText);

            String response = callOpenAI(prompt);
            return parseResponse(response);

        } catch (Exception e) {
            System.out.println("OPENAI HAD AN ISSUE PARSING");
            e.printStackTrace();
        }
        return null;
    }

    private String callOpenAI(String prompt) {
        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(API_KEY)
                .build();

        ChatCompletion completion = client.chat().completions().create(
                ChatCompletionCreateParams.builder()
                        .model(ChatModel.GPT_4O)
                        .maxTokens(4096)
                        .addUserMessage(prompt)
                        .build()
        );

        return completion.choices().get(0).message().content().orElse("");
    }

    private FightCard parseResponse(String json) throws Exception {
        String cleaned = json.replaceAll("```json", "").replaceAll("```", "").trim();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(cleaned, FightCard.class);
    }

    private String loadFormat() throws Exception {
        return new String(getClass().getResourceAsStream("/prompts/fight-card-format.json").readAllBytes());
    }
}