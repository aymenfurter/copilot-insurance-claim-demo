package com.microsoft.openai.samples.insurancedemo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.SKBuilders;
import com.microsoft.semantickernel.textcompletion.CompletionSKFunction;
import com.microsoft.semantickernel.textcompletion.TextCompletion;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.concurrent.CountDownLatch;
import com.microsoft.openai.samples.insurancedemo.client.OpenAIVisionClient;
import com.microsoft.openai.samples.insurancedemo.model.VisionResponse;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {"server.port=8080"})
@Disabled
public class OpenAITest {
    private String readFileAsBase64(String path) {
        String base64 = "data:image/png;base64,";
        try {
            File file = new File(getClass().getClassLoader().getResource(path).getFile());
            byte[] fileContent = Files.readAllBytes(file.toPath());
            base64 = base64 + Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }
    
    @Test 
    public void testVision() {
        VisionResponse response = null;
        try {
            OpenAIVisionClient client = new OpenAIVisionClient();
            response = client.sendVisionRequest(readFileAsBase64("car.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("=====================================");
        System.out.println("Vision Response: " + response.getResponseText());
        System.out.println("=====================================");
    }
}
