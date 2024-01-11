package com.microsoft.openai.samples.insurancedemo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.core.credential.AzureKeyCredential;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.SKBuilders;
import com.microsoft.semantickernel.textcompletion.CompletionSKFunction;
import com.microsoft.semantickernel.textcompletion.TextCompletion;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.microsoft.openai.samples.insurancedemo.client.OpenAIVisionClient;
import com.microsoft.openai.samples.insurancedemo.model.VisionResponse;
import com.microsoft.openai.samples.insurancedemo.service.VisionService;

public class OpenAITest {
    private static String imageBase64 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAApgAAAKYB3X3/OAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAANCSURBVEiJtZZPbBtFFMZ/M7ubXdtdb1xSFyeilBapySVU8h8OoFaooFSqiihIVIpQBKci6KEg9Q6H9kovIHoCIVQJJCKE1ENFjnAgcaSGC6rEnxBwA04Tx43t2FnvDAfjkNibxgHxnWb2e/u992bee7tCa00YFsffekFY+nUzFtjW0LrvjRXrCDIAaPLlW0nHL0SsZtVoaF98mLrx3pdhOqLtYPHChahZcYYO7KvPFxvRl5XPp1sN3adWiD1ZAqD6XYK1b/dvE5IWryTt2udLFedwc1+9kLp+vbbpoDh+6TklxBeAi9TL0taeWpdmZzQDry0AcO+jQ12RyohqqoYoo8RDwJrU+qXkjWtfi8Xxt58BdQuwQs9qC/afLwCw8tnQbqYAPsgxE1S6F3EAIXux2oQFKm0ihMsOF71dHYx+f3NND68ghCu1YIoePPQN1pGRABkJ6Bus96CutRZMydTl+TvuiRW1m3n0eDl0vRPcEysqdXn+jsQPsrHMquGeXEaY4Yk4wxWcY5V/9scqOMOVUFthatyTy8QyqwZ+kDURKoMWxNKr2EeqVKcTNOajqKoBgOE28U4tdQl5p5bwCw7BWquaZSzAPlwjlithJtp3pTImSqQRrb2Z8PHGigD4RZuNX6JYj6wj7O4TFLbCO/Mn/m8R+h6rYSUb3ekokRY6f/YukArN979jcW+V/S8g0eT/N3VN3kTqWbQ428m9/8k0P/1aIhF36PccEl6EhOcAUCrXKZXXWS3XKd2vc/TRBG9O5ELC17MmWubD2nKhUKZa26Ba2+D3P+4/MNCFwg59oWVeYhkzgN/JDR8deKBoD7Y+ljEjGZ0sosXVTvbc6RHirr2reNy1OXd6pJsQ+gqjk8VWFYmHrwBzW/n+uMPFiRwHB2I7ih8ciHFxIkd/3Omk5tCDV1t+2nNu5sxxpDFNx+huNhVT3/zMDz8usXC3ddaHBj1GHj/As08fwTS7Kt1HBTmyN29vdwAw+/wbwLVOJ3uAD1wi/dUH7Qei66PfyuRj4Ik9is+hglfbkbfR3cnZm7chlUWLdwmprtCohX4HUtlOcQjLYCu+fzGJH2QRKvP3UNz8bWk1qMxjGTOMThZ3kvgLI5AzFfo379UAAAAASUVORK5CYII=";
    @Test
    public void testOpenAIService() {
        String ENDPOINT = System.getenv("AZURE_OPEN_AI_ENDPOINT");
        String API_KEY = System.getenv("AZURE_OPEN_AI_KEY");

        OpenAIAsyncClient client = new OpenAIClientBuilder()
                .endpoint(ENDPOINT)
                .credential(new AzureKeyCredential(API_KEY))
                .buildAsyncClient();

        TextCompletion chatCompletion = SKBuilders.chatCompletion()
                .withOpenAIClient(client)
                .withModelId("gpt-4-32k")
                .build();

        Kernel kernel = SKBuilders.kernel()
                .withDefaultAIService(chatCompletion)
                .build();

        String prompt = "There was a barbeque festival in the city.\n" + "Summarize the content above.";

        CompletionSKFunction summarize = kernel.getSemanticFunctionBuilder()
                .withPromptTemplate(prompt)
                .withFunctionName("summarize")
                .withRequestSettings(SKBuilders.completionRequestSettings()
                    .temperature(0.4)
                    .topP(1)
                    .maxTokens(100)
                    .modelId("gpt-4-32k")
                    .build())
                .build();

        CountDownLatch latch = new CountDownLatch(1);

        summarize.invokeAsync("There was a barbeque festival in the city.")
            .subscribe(
                context -> {
                    String result = context.getResult();
                    assertNotNull(result, "The result should not be null");
                    assertFalse(result.isEmpty(), "The result should not be empty");
                    System.out.println("Summarization Result: " + result);
                    latch.countDown();
                },
                error -> {
                    fail("Error occurred: " + error.getMessage());
                    latch.countDown();
                },
                () -> System.out.println("Completed"));
    }

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
        String prompt = "1. What kind of damage is this? (Car Insurance, house insurance, pet insurance, other)" +
                        "2. What kind of car damage is it? (Parking damage, thief, broken glass, other)" +
                        "3. What is the car make?" +
                        "4. What is the car model?" +
                        "5. What is the license number?" +
                        "6. What is the estimated damage cost (<1000 CHF for minor damage, > 5000 CHF for medium ,  10000 CHF for severe." +
                        "7. Explain the situation in the picture";

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
