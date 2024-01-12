package com.microsoft.openai.samples.insurancedemo.model;

public class VisionRequest {

    private String imagePath;

    public VisionRequest() {
    }

    public VisionRequest(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
