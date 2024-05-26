package com.scezen.forge.service;

import org.springframework.ai.image.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * This service generates an image from a prompt using OPENAI API.
 */
@Service
public class ImageGenService {

    private final ImageClient imageClient;
    private final FileService fileService;

    public ImageGenService(ImageClient imageClient, FileService fileService) {
        this.imageClient = imageClient;
        this.fileService = fileService;
    }

    public ImageResponse generateImage(String promptContent) {
        ImageOptions imageOptions = ImageOptionsBuilder.builder()
                .withN(1)
                .withHeight(1024)
                .withWidth(1024)
                .build();

        return imageClient.call(new ImagePrompt(promptContent, imageOptions));
    }

    public File saveImage(String imageUrl, String fileName) throws IOException {
        return fileService.saveFileFromUrl(imageUrl, fileName);
    }
}
