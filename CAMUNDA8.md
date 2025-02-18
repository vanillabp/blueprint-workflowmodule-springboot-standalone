![VanillaBP](readme/vanillabp-headline.png)

# Running standalone blueprint using Camunda 8

These are a set of minimal instructions. Read more in the  [Camunda 8 Docs](https://docs.camunda.io/)

## Setup Instructions

1. **Download** the [Camunda 8 Docker Compose ZIP](https://docs.camunda.io/docs/self-managed/setup/deploy/local/docker-compose/).
2. **Extract** the files and navigate into the directory.
3. **Start Camunda 8** by using:
   ```bash
   docker compose up -d
   ```
4. Wait a few minutes for the system to initialize.

## Run Configuration
1. Build the project:
    ```shell
    mvn clean package
    ```
2. To run the application using the `camunda8` Spring profile:
    ```shell
    java -jar /target/demo.jar --spring.profiles.active=camunda8
    ```

## Tenant Configuration

You **DON'T** have to configure a new tenant as they are deactivated by default.
However, we recommend using tenants, especially in more complex projects, as they provide a structured way to separate processes across different business units, customers, or environments.
Read more on tenants and multi-tenancy in the [Camunda docs](https://docs.camunda.org/manual/latest/user-guide/process-engine/multi-tenancy/) and the [VanillaBP Camunda 8 Adapter](https://github.com/camunda-community-hub/vanillabp-camunda8-adapter/blob/main/spring-boot/README.md#using-camunda-multi-tenancy).

### Run blueprint using tenants

1. In the `application-camunda8.yaml` change `use-tenants: false` -> `true`
2. Make sure the Camunda 8 docker compose is up.
3. Open Identity: [http://localhost:8084](http://localhost:8084)
4. Login:
    - **Username:** `demo`
    - **Password:** `demo`
5. Create a new tenant:
    - Go to **Tenants** → **Create Tenant**
    - Set the Tenants **Name** and **ID** as `standalone` (Name of the Spring-boot application).
6. Assign user to tenant:
    - Go to **Assigned users** → **Assign users** → type/select the demo user
7. Assign applications to tenant:
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
