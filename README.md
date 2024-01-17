# Can AI Simplify Insurance Claim Filing?
<img src="architecture-overview.png?raw=true" width="900px">

This repository demonstrates an innovative approach to insurance claim processing. It combines traditional methods with an advanced "intelligent app" that utilizes GPT-4 Vision for initial claim analysis and Semantic Kernel for processing requests.

## Features
The application offers two distinct methods for filing insurance claims:
- **Traditional Claim Filing**: A standard, user-friendly way to submit insurance claims.
- **Intelligent App**: An advanced option that employs GPT-4 Vision for analyzing claim details and Semantic Kernel for policy coverage decisions.

<img src="screenshot.png?raw=true" width="350px">

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
