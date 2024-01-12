package com.microsoft.openai.samples.insurancedemo;

import org.junit.jupiter.api.Disabled;
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

import com.microsoft.openai.samples.insurancedemo.model.InsuranceResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {"server.port=8080"})
public class ImageAnalysisControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAnalyzeImage() throws Exception {
        String imageUrl = "https://github.com/aymenfurter/assets/blob/main/car.png?raw=true";

        ByteArrayResource imageResource = downloadImage(imageUrl);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new HttpEntity<>(imageResource, createImageHeaders()));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, createMultipartHeaders());
        ResponseEntity<InsuranceResponse> response = restTemplate.postForEntity("/api/image-analysis/upload", requestEntity, InsuranceResponse.class);
        assertNotNull(response.getBody());
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
