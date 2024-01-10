package com.microsoft.openai.samples.insurancedemo;

public record InsurancePolicy(String policyID, String customerID, String startDate, String endDate, double totalPremium, Vehicle vehicle, CoverageDetails coverageDetails) {}