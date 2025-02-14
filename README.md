![VanillaBP](readme/vanillabp-headline.png)

# blueprint-workflowmodule-springboot-standalone

A **blueprint** of a standalone Spring Boot application demonstrating how to integrate [VanillaBP SPI](https://github.com/vanillabp/spi-for-java) for BPMN-based workflows. This example covers a very minimal set of scenarios for developing business process applications and serves as a starting point for more complex use cases.

![demo.bpmn](readme/Standalone_BPMN_Process.png)

## Getting Started

1. **Clone the Repository**
   ```bash
   git clone https://github.com/stephanpellian/blueprint-workflowmodule-springboot-standalone.git
    ```
2. **Build the Project**
   ```bash
   mvn clean install
    ```
3. **Start the Project**
   ```bash
   mvn spring-boot:run
   ```

The Base-URL for this project is [http://localhost:8080](http://localhost:8080). See [Usage](##Usage) for all Endpoints.

## Usage

The demo exposes a REST-API:

* *Welcome-Page:*  [http://localhost:8080/home](http://localhost:8080/home)
* *Start a Workflow with id 1 and **NO** UserTask*: [http://localhost:8080/1/start](http://localhost:8080/1/start)
* *Start a Workflow with id 1 and UserTask*: [http://localhost:8080/1/start?wantUsertask=true](http://localhost:8080/1/start?wantUsertask=true)
* *Complete UserTask and end the Workflow*: [http://localhost:8080/1/{taskId}](http://localhost:8080/1/{taskId})

*Hint:* To complete a UserTask you need to require the taskId from the Logger.
## Noteworthy & Contributors

VanillaBP was developed by [Phactum](https://www.phactum.at) with the intention of giving back to the community as it has benefited the community in the past.\
![Phactum](readme/phactum.png)

## License

Copyright 2025 Phactum Softwareentwicklung GmbH

Licensed under the Apache License, Version 2.0