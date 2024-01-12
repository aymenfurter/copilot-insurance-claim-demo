package com.microsoft.openai.samples.insurancedemo.plugin;

import com.azure.core.http.HttpClient;
import com.azure.core.http.HttpMethod;
import com.azure.core.http.HttpRequest;
import com.microsoft.semantickernel.orchestration.SKContext;
import com.microsoft.semantickernel.skilldefinition.annotations.DefineSKFunction;
import com.microsoft.semantickernel.skilldefinition.annotations.SKFunctionParameters;
import reactor.core.publisher.Mono;

public class InsurancePolicyPlugin {
    private final HttpClient httpClient;
    private final String baseUrl;

    public InsurancePolicyPlugin() {
        this.httpClient = HttpClient.createDefault();
        this.baseUrl = "http://localhost:8080/api/insurance-policies";
    }

    public InsurancePolicyPlugin(HttpClient httpClient, String baseUrl) {
        this.httpClient = httpClient;
        this.baseUrl = baseUrl;
    }

    @DefineSKFunction(description = "Fetch the list of insurance policies", name = "GetPolicies")
    public Mono<String> getPoliciesAsync(SKContext context) {
        HttpRequest request = new HttpRequest(HttpMethod.GET, baseUrl);
        return httpClient.send(request).flatMap(response -> response.getBodyAsString());
    }
}
