package com.fflmanager.LLM;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fflmanager.dto.FightCard;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;




/**
 * Author: Sajal Gupta
 * Date: 3/12/26 10:38 AM
 */
@Service
public class ClaudeParser implements FightCardParser {

    private String API_KEY;

    public ClaudeParser() {
        try {
            Properties props = new Properties();
            props.load(getClass().getResourceAsStream("/application.properties"));
            API_KEY = props.getProperty("anthropic.api.key");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public FightCard parse(String rawText){
		try {
			String prompt = """
					Extract all fights from the following UFC fight card text.
					Return ONLY a JSON object matching this exact format, no explanation:
					%s

					Fight card text:
					%s
					""".formatted(loadFormat(), rawText);

			String response = callClaude(prompt);
			return parseResponse(response);

		} catch (Exception e) {
			System.out.println("CLAUDE HAD AN ISSUE PARSING");
			e.printStackTrace();
		}
		return null;
	}

    /**
     *
     * @param prompt
     * @return
     */
    private String callClaude(String prompt) {
        AnthropicClient client = AnthropicOkHttpClient.builder()
                .apiKey(API_KEY)
                .build();

        Message message = client.messages().create(
                MessageCreateParams.builder()
                        .model(Model.CLAUDE_SONNET_4_6)
                        .maxTokens(1024)
                        .addUserMessage(prompt)
                        .build()
        );

        return message.content().get(0).asText().text();
    }

	/**
	 * 
	 * @param json
	 * @return
	 * @throws Exception
	 */
    private FightCard parseResponse(String json) throws Exception {
        String cleaned = json.replaceAll("```json", "").replaceAll("```", "").trim();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(cleaned, FightCard.class);
    }

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private String loadFormat() throws Exception {
		return new String(getClass().getResourceAsStream("/prompts/fight-card-format.json").readAllBytes());
	}
}
