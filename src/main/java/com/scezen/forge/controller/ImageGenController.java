package com.scezen.forge.controller;

import com.scezen.forge.config.ForgeProperties;
import com.scezen.forge.service.ImageGenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * ImageGenController is a REST controller responsible for handling requests to generate images based on a predefined prompt.
 *
 * This controller performs the following tasks:
 * 1. Retrieves the prompt template and list of verticals from the application configuration (application.yml).
 * 2. Iterates over each vertical, replaces placeholders in the prompt with the current vertical, and generates an image using the ImageGenService.
 * 3. Saves each generated image to the specified output directory with a filename corresponding to the vertical.
 * 4. Logs the success of each image generation and the total time taken for all generations.
 *
 * Dependencies:
 * - ForgeProperties: Provides configuration properties including the prompt template, list of verticals, and output directory.
 * - ImageGenService: Handles the actual image generation and saving process.
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

        String promptTemplate = forgeProperties.getPrompt();

        for (String vertical : forgeProperties.getVerticals()) {
            String promptContent = promptTemplate.replace("{VERTICAL}", vertical);
            String imageUrl = imageGenService.generateImage(promptContent).getResult().getOutput().getUrl();

            String fileName = vertical.toLowerCase() + "-forged.png";
            imageGenService.saveImage(imageUrl, fileName);

            logger.info("Image for vertical '{}' saved successfully to {}", vertical, forgeProperties.getOutputDirectory());
        }

        return "Image generation for all verticals completed.";
    }
}
