package com.scezen.forge.service;

import com.scezen.forge.config.ForgeProperties;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * This service handles file operations such as downloading and saving files.
 */
@Service
public class FileService {

    private final ForgeProperties forgeProperties;

    public FileService(ForgeProperties forgeProperties) {
        this.forgeProperties = forgeProperties;
    }

    public File saveFileFromUrl(String fileUrl, String fileName) throws IOException {
        try (InputStream inputStream = downloadFile(fileUrl);
             FileOutputStream outputStream = createOutputStream(fileName)) {
            writeToFile(inputStream, outputStream);
        }
        return new File(forgeProperties.getOutputDirectory(), fileName);
    }

    private InputStream downloadFile(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        return url.openStream();
    }

    private FileOutputStream createOutputStream(String fileName) throws IOException {
        String outputDirectory = forgeProperties.getOutputDirectory();
        File outputFile = new File(outputDirectory, fileName);
        return new FileOutputStream(outputFile);
    }

    private void writeToFile(InputStream inputStream, FileOutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
    }
}
