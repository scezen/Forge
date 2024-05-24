package com.scezen.forge.service;

import org.springframework.ai.image.*;
import org.springframework.stereotype.Service;

/**
 * This service generates an image from a prompt (imageprompt.st) using OPENAI API.
 */
@Service
public class ImageGenService {

    private final ImageClient imageClient;

    public ImageGenService(ImageClient imageClient) {
        this.imageClient = imageClient;
    }

    public ImageResponse generateImage(String promptContent) {
        ImageOptions imageOptions = ImageOptionsBuilder.builder()
                .withN(1)
                .withHeight(1024)
                .withWidth(1024)
                .build();

        return imageClient.call(new ImagePrompt(promptContent, imageOptions));
    }
}
