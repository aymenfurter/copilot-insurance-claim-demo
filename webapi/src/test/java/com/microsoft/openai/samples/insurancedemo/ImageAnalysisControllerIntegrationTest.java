package com.microsoft.openai.samples.insurancedemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImageAnalysisControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAnalyzeImage() throws Exception {
        // URL of the image
        String imageUrl = "https://github.com/aymenfurter/assets/blob/main/car.png?raw=true";

        // Download the image
        ByteArrayResource imageResource = downloadImage(imageUrl);

        // Prepare the file upload request
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new HttpEntity<>(imageResource, createImageHeaders()));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, createMultipartHeaders());

        // Send the request
        ResponseEntity<InsuranceResponse> response = restTemplate.postForEntity("/api/image-analysis/upload", requestEntity, InsuranceResponse.class);


        // Assert the response
        assertNotNull(response.getBody());

        assertEquals("Image analyzed successfully.", response.getBody().getReplyText());
        // More assertions based on your logic
    }

    private ByteArrayResource downloadImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();

        return new ByteArrayResource(inputStream.readAllBytes()) {
            @Override
            public String getFilename() {
                return "car.png";
            }
        };
    }

    private HttpHeaders createImageHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return headers;
    }

    private HttpHeaders createMultipartHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return headers;
    }
}
