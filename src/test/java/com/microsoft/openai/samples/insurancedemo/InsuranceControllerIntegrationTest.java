package com.microsoft.openai.samples.insurancedemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InsuranceControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetInsurancePolicies() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/insurance-policies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].policyID").value("12345"))
                .andExpect(jsonPath("$[0].vehicle.make").value("Toyota"))
                .andExpect(jsonPath("$[0].vehicle.color").value("Blue"))
                .andExpect(jsonPath("$[0].vehicle.plateNumber").value("CH-041975"));
    }
}

