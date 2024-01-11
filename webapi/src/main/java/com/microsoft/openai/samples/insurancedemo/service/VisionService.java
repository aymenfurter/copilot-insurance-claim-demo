package com.microsoft.openai.samples.insurancedemo.service;

import com.microsoft.openai.samples.insurancedemo.client.OpenAIVisionClient;
import com.microsoft.openai.samples.insurancedemo.model.VisionResponse;
import com.microsoft.openai.samples.insurancedemo.util.FileUtil;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisionService {

    private final OpenAIVisionClient visionClient;

    @Autowired
    public VisionService(OpenAIVisionClient visionClient) {
        this.visionClient = visionClient;
    }

    public VisionResponse processVisionRequest(String imagePath) throws IOException {
        String base64Image = FileUtil.readFileAsBase64(imagePath);
        return visionClient.sendVisionRequest(base64Image);
    }
}
