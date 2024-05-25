package com.scezen.forge.controller;

import com.scezen.forge.config.ForgeProperties;
import com.scezen.forge.service.ImageGenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * This controller handles image generation requests.
 */
@RestController
public class ImageGenController {

    private static final Logger logger = LoggerFactory.getLogger(ImageGenController.class);
    private final ImageGenService imageGenService;
    private final ForgeProperties forgeProperties;

    @Autowired
    public ImageGenController(ImageGenService imageGenService, ForgeProperties forgeProperties) {
        this.imageGenService = imageGenService;
        this.forgeProperties = forgeProperties;
    }

    @GetMapping("/generate-image")
    public String generateImage(HttpServletResponse response) throws IOException {

        String promptContent = forgeProperties.getPrompt();

        String imageUrl = imageGenService.generateImage(promptContent).getResult().getOutput().getUrl();

        String fileName = "generated-image.png";
        imageGenService.saveImage(imageUrl, fileName);

        logger.info("Image saved successfully to " + forgeProperties.getOutputDirectory());

        return "Image generation completed.";
    }
}
