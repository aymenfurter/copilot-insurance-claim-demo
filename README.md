# How a Picture of Car Damage Can File Your Insurance Claim
<img src="architecture-overview.png?raw=true" width="900px">


## 1. Image Analysis for Claim Data Extraction
This initial step uses GPT-4 Turbo with Vision to analyze images submitted for insurance claims. It extracts information such as the **type of car damage**, the **car's make** and **model**, and the **license plate number**. This data is foundational for processing the claim.
- [View OpenAIVisionClient Java Class](https://github.com/aymenfurter/copilot-insurance-claim-demo/blob/main/src/main/java/com/microsoft/openai/samples/insurancedemo/client/OpenAIVisionClient.java)

## 2. Insurance Coverage Determination
In this phase, the semantic kernel is tasked with verifying whether the specific car insurance policy covers the identified damage. Utilizing the [Stepwise Planner](https://devblogs.microsoft.com/semantic-kernel/semantic-kernel-planners-stepwise-planner/), we can direct the kernel to utilize the `InsurancePolicyPlugin`. The goal is to find the relevant insurance policy and confirm coverage for the particular incident. This crucial step directly addresses whether the insurance policy covers the reported incident.
```
String ENDPOINT = System.getenv("AZURE_OPEN_AI_ENDPOINT");
String API_KEY = System.getenv("AZURE_OPEN_AI_KEY");

OpenAIAsyncClient client = new OpenAIClientBuilder()
.endpoint(ENDPOINT)
.credential(new AzureKeyCredential(API_KEY))
.buildAsyncClient();

Kernel kernel = SKBuilders.kernel()
.withDefaultAIService(SKBuilders.chatCompletion()
.withOpenAIClient(client)
.withModelId("gpt-4-32k")
.build())
.build();

kernel.importSkill(new InsurancePolicyPlugin(), "InsurancePolicyPlugin");
var goal = "Your goal is by using the InsurancePolicyPlugin to 1. find out which policy relates to the car accident report and 2. if the policy covers parking damage. Provide the policy information related to the car (if there is any) and inform the user if the damage is covered or not. Use an emoji in your response. \nInsurance Claim Information: "
+ responseText;
StepwisePlanner planner = new DefaultStepwisePlanner(kernel, null, null);
var plan = planner.createPlan(goal);
SKContext result = plan.invokeAsync().block();
return result.getResult();
```
- [View ImageAnalysisService Java Class](https://github.com/aymenfurter/copilot-insurance-claim-demo/blob/main/src/main/java/com/microsoft/openai/samples/insurancedemo/service/ImageAnalysisService.java#L81)

## 3. Custom Plugin Integration for Policy Verification
The `InsurancePolicyPlugin` is a custom plugin created for the kernel. It connects with the existing (mocked) policy system to fetch insurance policies. This plugin exemplifies a typical developer task when building AI apps - creating plugins that extend the kernel's capabilities by integrating with existing systems.
```
public class InsurancePolicyPlugin {
    private final HttpClient httpClient;
    private final String baseUrl = "http://localhost:8080/api/insurance-policies";
      // Constructor initializes the HttpClient
      public InsurancePolicyPlugin() {
          this.httpClient = HttpClient.createDefault();
      }
    
      // Function to fetch insurance policies
      @DefineSKFunction(description = "Fetch the list of insurance policies", name = "GetPolicies")
      public Mono<String> getPoliciesAsync(SKContext context) {
          HttpRequest request = new HttpRequest(HttpMethod.GET, baseUrl);
          return httpClient.send(request).flatMap(response -> response.getBodyAsString());
      }
}
```
- [InsurancePolicyPlugin Java Class](https://github.com/aymenfurter/copilot-insurance-claim-demo/blob/main/src/main/java/com/microsoft/openai/samples/insurancedemo/plugin/InsurancePolicyPlugin.java)

## Preview 
<img src="preview.gif?raw=true" width="350px">

## Prerequisites 
To deploy this application, please ensure you have the following dependencies installed:
* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Maven](https://maven.apache.org/download.cgi)
* [Git](https://git-scm.com/downloads)

## Setup Procedure
1. Clone the repository to your local machine.
2. Navigate to the project's root directory.
3. Run `mvn clean install` to build the project using Maven.
4. Before starting the application, set the endpoint and key:
    - `export AZURE_OPEN_AI_ENDPOINT=https://<instance-name>.openai.azure.com/`
    - `export AZURE_OPEN_AI_KEY=<your-key>`
5. Start the application with `java -jar target/appname.jar` and access it via `http://localhost:8080`.

## Future Improvements
- [ ] Implement GPT-4 Vision analysis directly within Semantic Kernel (once supported).
