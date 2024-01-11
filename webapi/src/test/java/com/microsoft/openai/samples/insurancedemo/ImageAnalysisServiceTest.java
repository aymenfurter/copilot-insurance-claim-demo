package com.microsoft.openai.samples.insurancedemo;

import static org.junit.jupiter.api.Assertions.*;

import com.microsoft.openai.samples.insurancedemo.model.*;
import com.microsoft.openai.samples.insurancedemo.service.ImageAnalysisService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {"server.port=8080"})
@Disabled
public class ImageAnalysisServiceTest {
    private ImageAnalysisService service;

    @BeforeEach
    void setUp() {
        service = new ImageAnalysisService();
    }

    @Test
    void testParseValidResponse() {
        String validResponse = "1. CAR_INSURANCE\n"
                             + "2. PARKING_DAMAGE\n"
                             + "3. Toyota\n"
                             + "4. Corolla\n"
                             + "5. ZH041975\n"
                             + "6. MINOR\n"
                             + "7. The car has a minor scratch on the rear bumper.";
        InsuranceResponse response = service.parseResponse(validResponse);

        assertEquals(DamageType.CAR_INSURANCE, response.getDamageType());
        assertEquals(CarInsuranceType.PARKING_DAMAGE, response.getCarInsuranceType());
        assertEquals("Toyota", response.getCarMake());
        assertEquals("Corolla", response.getCarModel());
        assertEquals("ZH041975", response.getLicensePlateNumber());
        assertEquals(CostCategory.MINOR, response.getEstimatedCostCategory());
        assertEquals("The car has a minor scratch on the rear bumper.", response.getSituationExplanation());
    }

    @Test
    void testParseResponseWithInvalidFormat() {
        String invalidResponse = "Invalid response format";
        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.parseResponse(invalidResponse);
        });

        String expectedMessage = "Invalid response format";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testPlanner() {
        String validResponse = "1. CAR_INSURANCE\n"
                             + "2. PARKING_DAMAGE\n"
                             + "3. Toyota\n"
                             + "4. Corolla\n"
                             + "5. ZH041975\n"
                             + "6. MINOR\n"
                             + "7. The car has a minor scratch on the rear bumper.";

        String response = service.replyText(validResponse);
        assertNotNull(response);
        System.out.println("====================================");
        System.out.println(response);
        System.out.println("====================================");
    }
}
