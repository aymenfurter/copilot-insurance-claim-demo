package com.microsoft.openai.samples.insurancedemo;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//import io.swagger.v3.oas.annotations.Hidden;


@RestController
@RequestMapping("/api/image-analysis")
//@Hidden
public class ImageAnalysisController {

    @PostMapping("/upload")
    public InsuranceResponse analyzeImage(@RequestParam("file") MultipartFile file) {
        // Implement your image analysis logic here
        // For now, this is a placeholder implementation

        // Placeholder data - you should replace this with the actual extraction and analysis logic
        String replyText = "Image analyzed successfully.";
        DamageType damageType = DamageType.CAR_INSURANCE;
        CarInsuranceType carInsuranceType = CarInsuranceType.THEFT; // Example, choose appropriate value
        String carModel = "Extracted Car Model"; // Extracted from the image
        String licensePlateNumber = "Extracted License Plate"; // Extracted from the image
        CostCategory estimatedCostCategory = CostCategory.MINOR; // Deduced from the image
        String situationExplanation = "Extracted situation explanation."; // Extracted/deduced from the image

        return new InsuranceResponse(
            replyText,
            damageType,
            carInsuranceType,
            carModel,
            licensePlateNumber,
            estimatedCostCategory,
            situationExplanation
        );
    }
}
