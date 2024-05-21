package com.scezen.forge.controller;

import com.scezen.forge.service.PromptGenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
public class PromptGenController {

    private final PromptGenService promptGenService;

    public PromptGenController(PromptGenService promptGenService) {
        this.promptGenService = promptGenService;
    }

    @GetMapping("/getprompt")
    public String getPrompt() throws IOException {
        return promptGenService.generatePrompt();
    }
}
