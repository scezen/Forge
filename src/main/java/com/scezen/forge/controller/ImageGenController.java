package com.scezen.forge.controller;

import com.scezen.forge.service.ImageGenService;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@RestController
public class ImageGenController {

    private final ImageGenService imageGenService;

    @Value("classpath:/prompts/imageprompt.st")
    private Resource imagePromptResource;

    public ImageGenController(ImageGenService imageGenService) {
        this.imageGenService = imageGenService;
    }

    @GetMapping("/getimage")
    public void generateImage(HttpServletResponse response) throws IOException {

        PromptTemplate promptTemplate = new PromptTemplate(imagePromptResource);

        Prompt prompt = promptTemplate.create();

        String promptContent = prompt.getContents();

        ImageResponse imageResponse = imageGenService.generateImage(promptContent);

        String imageUrl = imageResponse.getResult().getOutput().getUrl();

        response.sendRedirect(imageUrl);
    }
}
