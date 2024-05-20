package com.scezen.forge;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
public class ImageModal {

    private final ChatClient chatClient;

    public ImageModal(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/getprompt")
    public String getPrompt() throws IOException {

        byte[] imageData = new ClassPathResource("/images/illustration.jpg").getContentAsByteArray();
        UserMessage userMessage = new UserMessage("Based on the following flaticon, generate a description focusing on its style, color palette, mood, without referencing the specific object depicted. Create the description in a templated format where {PRODUCT} can be replaced by any object to generate a new image with the same style.",
                new Media(MimeTypeUtils.IMAGE_JPEG, imageData));

        ChatResponse response = chatClient.call(new Prompt(userMessage));
        return response.getResult().getOutput().getContent();

    }

}
