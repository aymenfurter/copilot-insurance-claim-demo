package com.microsoft.openai.samples.insurancedemo.controller;

import com.microsoft.openai.samples.insurancedemo.model.InsurancePolicy;
import com.microsoft.openai.samples.insurancedemo.service.InsurancePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/insurance-policies")
public class InsurancePolicyController {

    @Autowired
    private InsurancePolicyService insurancePolicyService;

    @GetMapping
    public List<InsurancePolicy> getInsurancePolicies() {
        return insurancePolicyService.getAllInsurancePolicies();
    }
}