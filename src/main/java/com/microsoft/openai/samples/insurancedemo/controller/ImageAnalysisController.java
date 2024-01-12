package com.microsoft.openai.samples.insurancedemo.controller;

import com.microsoft.openai.samples.insurancedemo.model.InsuranceResponse;
import com.microsoft.openai.samples.insurancedemo.service.ImageAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image-analysis")
public class ImageAnalysisController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageAnalysisController.class);

    @Autowired
    private ImageAnalysisService imageAnalysisService;

    @PostMapping("/upload")
    @CrossOrigin(origins = "*")
    public InsuranceResponse analyzeImage(@RequestParam("file") MultipartFile file) {
        try {
            return imageAnalysisService.analyzeImage(file);
        } catch (RuntimeException e) {
            LOGGER.error("Error processing image: {}", e.getMessage());
            throw e;
        }
    }
}