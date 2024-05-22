package com.scezen.forge.controller;

import com.scezen.forge.service.ImageGenService;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@RestController
public class ImageGenController {

    private final ImageGenService imageGenService;

    public ImageGenController(ImageGenService imageGenService) {
        this.imageGenService = imageGenService;
    }

    @GetMapping("/getimage")
    public void generateImage(HttpServletResponse response, @RequestParam("prompt") String prompt) throws IOException {

        ImageResponse imageResponse = imageGenService.generateImage(prompt);

        String imageUrl = imageResponse.getResult().getOutput().getUrl();

        response.sendRedirect(imageUrl);
    }
}
