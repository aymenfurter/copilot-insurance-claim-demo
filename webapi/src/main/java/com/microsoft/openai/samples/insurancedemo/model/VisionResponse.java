package com.microsoft.openai.samples.insurancedemo.model;

public class VisionResponse {

    private String responseText;

    public VisionResponse() {
    }

    public VisionResponse(String responseText) {
        this.responseText = responseText;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}
