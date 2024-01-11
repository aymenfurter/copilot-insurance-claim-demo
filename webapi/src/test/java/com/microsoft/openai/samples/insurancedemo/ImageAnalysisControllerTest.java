package com.microsoft.openai.samples.insurancedemo;

import static org.junit.jupiter.api.Assertions.*;

import com.microsoft.openai.samples.insurancedemo.controller.ImageAnalysisController;
import com.microsoft.openai.samples.insurancedemo.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImageAnalysisControllerTest {

    private ImageAnalysisController controller;

    @BeforeEach
    void setUp() {
        controller = new ImageAnalysisController();
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
        InsuranceResponse response = controller.parseResponse(validResponse);

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
            controller.parseResponse(invalidResponse);
        });

        String expectedMessage = "Invalid response format";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
