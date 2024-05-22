package com.scezen.forge.controller;

import com.scezen.forge.service.ImageGenService;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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

        String promptContent = new PromptTemplate(imagePromptResource).create().getContents();

        String imageUrl = imageGenService.generateImage(promptContent).getResult().getOutput().getUrl();

        response.sendRedirect(imageUrl);
    }
}
