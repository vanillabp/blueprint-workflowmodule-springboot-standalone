![VanillaBP](readme/vanillabp-headline.png)

# Camunda 8 Adapter standalone blueprint

## Prerequisites
Ensure you have the following installed:
- **Docker** (20.10.16+)
- **Docker Compose** (1.27.0+)

## Setup Instructions

1. **Download** the [Camunda 8 Docker Compose](https://docs.camunda.io/docs/self-managed/setup/deploy/local/docker-compose/).
2. **Extract** the files and navigate into the directory.
3. **Start Camunda 8** using:
   ```bash
   docker compose up -d
   ```
4. wait a few minutes for the system to initialize.

*Hint:* We recommend using Docker Desktop.

## Run Configuration

To run the Camunda 8 spring profile you have to either create a new run configuration in your IDE or use this command after rebuilding your project:
```shell
    java -jar .\target\demo.jar --spring.profiles.active=camunda8
```

## Identity Configuration

You DON'T have to configure a new tenant as they are deactivated by default.
However, we recommend using tenants, especially in more complex projects, as they provide a structured way to separate data and processes across different business units, customers, or environments.
Read more on Tenants and Multi-Tenancy in the [Camunda docs](https://docs.camunda.org/manual/latest/user-guide/process-engine/multi-tenancy/) and the [VanillaBP Camunda 8 Adapter](https://github.com/camunda-community-hub/vanillabp-camunda8-adapter/blob/main/spring-boot/README.md#using-camunda-multi-tenancy) on how to use Multi-Tenancy.

1. Open Identity: [http://localhost:8084](http://localhost:8084)
2. Login:
    - **Username:** `demo`
    - **Password:** `demo`
3. Create a new tenant:
    - Go to **Tenants** → **Create Tenant**
    - Set the Tenants **Name** and **ID** as `standalone` (Name of the Spring-boot application).
4. Assign user to tenant:
    - Go to **Assigned users** → **Assign users** → type/select the demo user
5. Assign applications to tenant:
    - Go to the **Assigned applications** tab
    - Click **Assign application** and add:
        - `identity`
        - `operate`
        - `zeebe`


Now your Camunda 8 profile is ready for use!

## Noteworthy & Contributors

VanillaBP was developed by [Phactum](https://www.phactum.at) with the intention of giving back to the community as it has benefited the community in the past.\
![Phactum](readme/phactum.png)

## License

Copyright 2025 Phactum Softwareentwicklung GmbH

Licensed under the Apache License, Version 2.0
