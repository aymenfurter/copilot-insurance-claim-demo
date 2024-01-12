package com.microsoft.openai.samples.insurancedemo.model;

public class InsuranceResponse {
    private String replyText;
    private DamageType damageType;
    private CarInsuranceType carInsuranceType;
    private String carModel;
    private String carMake;
    private String licensePlateNumber;
    private CostCategory estimatedCostCategory;
    private String situationExplanation;

    public InsuranceResponse(String replyText, DamageType damageType, CarInsuranceType carInsuranceType, String carMake, String carModel, String licensePlateNumber, CostCategory estimatedCostCategory, String situationExplanation) {
        this.replyText = replyText;
        this.damageType = damageType;
        this.carInsuranceType = carInsuranceType;
        this.carModel = carModel;
        this.carMake = carMake;
        this.licensePlateNumber = licensePlateNumber;
        this.estimatedCostCategory = estimatedCostCategory;
        this.situationExplanation = situationExplanation;

    }
 

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public CarInsuranceType getCarInsuranceType() {
        return carInsuranceType;
    }

    public void setCarInsuranceType(CarInsuranceType carInsuranceType) {
        this.carInsuranceType = carInsuranceType;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carBrand) {
        this.carMake = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public CostCategory getEstimatedCostCategory() {
        return estimatedCostCategory;
    }

    public void setEstimatedCostCategory(CostCategory estimatedCostCategory) {
        this.estimatedCostCategory = estimatedCostCategory;
    }

    public String getSituationExplanation() {
        return situationExplanation;
    }

    public void setSituationExplanation(String situationExplanation) {
        this.situationExplanation = situationExplanation;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }
}