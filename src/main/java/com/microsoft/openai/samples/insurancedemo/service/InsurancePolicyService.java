package com.microsoft.openai.samples.insurancedemo.service;

import com.microsoft.openai.samples.insurancedemo.model.CoverageDetails;
import com.microsoft.openai.samples.insurancedemo.model.InsurancePolicy;
import com.microsoft.openai.samples.insurancedemo.model.Vehicle;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsurancePolicyService {

    private List<InsurancePolicy> insurancePolicies = Arrays.asList(
        createPolicy("A987544", "67890", "Toyota", "Corolla", "Blue", 2020, "ZH 041975", true, false),
        createPolicy("B384834", "67890", "Toyota", "Camry", "Blue", 1995, "ZH 865506", true, true),
        createPolicy("C84H229", "67890", "Honda", "Civic", "Black", 2018, "AG 153577", false, true)
    );

    public List<InsurancePolicy> getAllInsurancePolicies() {
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
