package com.microsoft.openai.samples.insurancedemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;

@RestController
public class InsuranceController {

    private List<InsurancePolicy> insurancePolicies = Arrays.asList(
        createPolicy("12345", "67890", "Toyota", "Corolla", "Blue", 2020, "CH-041975", true, true),
        createPolicy("12346", "67891", "Toyota", "Camry", "Red", 2019, "CH-041976", true, false),
        createPolicy("12347", "67892", "Honda", "Civic", "Black", 2018, "CH-041977", false, true)
    );

    @GetMapping("/api/insurance-policies")
    public List<InsurancePolicy> getInsurancePolicies() {
        return insurancePolicies;
    }

    private InsurancePolicy createPolicy(String policyId, String customerId, String make, String model, String color, int year, String plateNumber, boolean comprehensive, boolean parkingDamage) {
        return new InsurancePolicy(
            policyId,
            customerId,
            "2024-01-01",
            "2025-01-01",
            1200.00,
            new Vehicle(make, model, color, year, plateNumber),
            new CoverageDetails(comprehensive, parkingDamage)
        );
    }
}
