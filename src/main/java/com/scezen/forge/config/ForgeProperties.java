package com.scezen.forge.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "forge")
public class ForgeProperties {

    private String prompt;
    private List<String> verticals;
    private String outputDirectory;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public List<String> getVerticals() {
        return verticals;
    }

    public void setVerticals(List<String> verticals) {
        this.verticals = verticals;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }
}
