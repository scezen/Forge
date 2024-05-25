package com.scezen.forge.service;

import com.scezen.forge.config.ForgeProperties;
import org.springframework.ai.image.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * This service generates an image from a prompt using OPENAI API.
 */
@Service
public class ImageGenService {

    private final ImageClient imageClient;
    private final ForgeProperties forgeProperties;

    public ImageGenService(ImageClient imageClient, ForgeProperties forgeProperties) {
        this.imageClient = imageClient;
        this.forgeProperties = forgeProperties;
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
        URL url = new URL(imageUrl);
        InputStream in = url.openStream();
        String outputDirectory = forgeProperties.getOutputDirectory();
        File outputFile = new File(outputDirectory, fileName);

        try (FileOutputStream out = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int n;
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
        } finally {
            in.close();
        }

        return outputFile;
    }
}
