package com.microsoft.openai.samples.insurancedemo.controller;

import com.microsoft.openai.samples.insurancedemo.client.OpenAIVisionClient;
import com.microsoft.openai.samples.insurancedemo.model.*;
import com.microsoft.openai.samples.insurancedemo.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/image-analysis")
public class ImageAnalysisController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageAnalysisController.class);

    @Autowired
    private OpenAIVisionClient openAIVisionClient;

    @PostMapping("/upload")
    public InsuranceResponse analyzeImage(@RequestParam("file") MultipartFile file) {
        try {
            VisionResponse response = openAIVisionClient.sendVisionRequest(FileUtil.convertMultiPartToFileBase64(file));
            return parseResponse(response.getResponseText());
        } catch (IOException e) {
            LOGGER.error("Error processing image: {}", e.getMessage());
            throw new RuntimeException("Error processing image upload");
        }
    }

    public InsuranceResponse parseResponse(String responseText) {
        if (!responseText.matches("(?s)1\\. .*2\\. .*3\\. .*4\\. .*5\\. .*6\\. .*7\\..*")) {
            throw new RuntimeException("Invalid response format");
        }
    
        String[] parts = responseText.split("\n");
        
        DamageType damageType = DamageType.valueOf(parts[0].split("\\.")[1].trim());
        CarInsuranceType carInsuranceType = CarInsuranceType.valueOf(parts[1].split("\\.")[1].trim());
        String carMake = parts[2].split("\\.")[1].trim();
        String carModel = parts[3].split("\\.")[1].trim();
        String licensePlateNumber = parts[4].split("\\.")[1].trim();
        CostCategory estimatedCostCategory = CostCategory.valueOf(parts[5].split("\\.")[1].trim());
        String situationExplanation = parts[6].substring(3).trim();
   

        return new InsuranceResponse(
            "Image analyzed successfully.",
            damageType,
            carInsuranceType,
            carMake,
            carModel,
            licensePlateNumber,
            estimatedCostCategory,
            situationExplanation
        );
    }
    
}
