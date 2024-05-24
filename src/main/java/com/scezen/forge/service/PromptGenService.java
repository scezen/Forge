package com.scezen.forge.service;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import java.io.IOException;

/**
 * This service generates prompts from a specified image using OPENAI API.
 */
@Service
public class PromptGenService {

    private final ChatClient chatClient;

    public PromptGenService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String generatePrompt() throws IOException {
        byte[] imageData = new ClassPathResource("/images/illustration.png").getContentAsByteArray();
        UserMessage userMessage = new UserMessage(
                "Based on the following flaticon, generate a prompt focusing on its style, color palette, and mood. Do not reference the specific object depicted. Instead, create the description in a templated format where {VERTICAL} can be replaced by any object to generate a new image with the same style. The prompt should be detailed enough to be used with the DALL-E API to generate icons for different product category. Please format the prompt with words like 'should' 'must' may' etc. Each guideline must be on line breaks to ensure readability and modularity of the prompt.",
                new Media(MimeTypeUtils.IMAGE_PNG, imageData)
        );

        ChatResponse response = chatClient.call(new Prompt(userMessage));
        return response.getResult().getOutput().getContent();
    }
}
