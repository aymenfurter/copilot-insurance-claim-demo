package com.microsoft.openai.samples.insurancedemo.util;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class FileUtil {
    public static String readFileAsBase64(String path) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + path);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(fileContent);
    }
    
    public static String convertMultiPartToFileBase64(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("The file cannot be null or empty.");
        }

        byte[] fileContent = file.getBytes();
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(fileContent);
    }
}
