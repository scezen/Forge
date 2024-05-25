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
import java.util.concurrent.TimeUnit;

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

    @GetMapping("/generate-images")
    public String generateImages() throws IOException {

        long startTime = System.currentTimeMillis();

        String promptTemplate = forgeProperties.getPrompt();

        for (String vertical : forgeProperties.getVerticals()) {
            String promptContent = promptTemplate.replace("{VERTICAL}", vertical);
            String imageUrl = imageGenService.generateImage(promptContent).getResult().getOutput().getUrl();

            String fileName = vertical.toLowerCase() + "-forged.png";
            imageGenService.saveImage(imageUrl, fileName);

            logger.info("Image for vertical '{}' saved successfully to {}", vertical, forgeProperties.getOutputDirectory());
        }

        long endTime = System.currentTimeMillis();
        long durationMillis = endTime - startTime;

        String durationFormatted;
        if (durationMillis >= TimeUnit.MINUTES.toMillis(1)) {
            long durationMinutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis);
            durationFormatted = durationMinutes + " minutes";
        } else {
            long durationSeconds = TimeUnit.MILLISECONDS.toSeconds(durationMillis);
            durationFormatted = durationSeconds + " seconds";
        }

        logger.info("All images generated successfully in {}.", durationFormatted);

        return "Image generation for all verticals completed.";
    }
}
